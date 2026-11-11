package com.jakubjirak.archbreadcrumbs.store

import com.intellij.openapi.Disposable
import com.intellij.openapi.components.Service
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.vfs.LocalFileSystem
import com.jakubjirak.archbreadcrumbs.model.Breadcrumb
import com.jakubjirak.archbreadcrumbs.model.BreadcrumbCategory
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList

@Suppress("TooManyFunctions")
@Service(Service.Level.PROJECT)
class BreadcrumbStore(private val project: Project) : Disposable {

    private val breadcrumbs: MutableList<Breadcrumb> = CopyOnWriteArrayList()
    private val listeners: MutableList<BreadcrumbChangeListener> = CopyOnWriteArrayList()

    fun interface BreadcrumbChangeListener {
        fun onBreadcrumbsChanged()
    }

    fun addListener(listener: BreadcrumbChangeListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: BreadcrumbChangeListener) {
        listeners.remove(listener)
    }

    fun loadBreadcrumbs() {
        try {
            val file = getStoreFile()
            if (!file.exists()) {
                LOG.info("No breadcrumbs file found at ${file.absolutePath}")
                return
            }
            val loaded = BreadcrumbJson.decode(file.readText())
            breadcrumbs.clear()
            breadcrumbs.addAll(loaded)
            LOG.info("Loaded ${breadcrumbs.size} breadcrumbs from ${file.name}")
            notifyListeners()
        } catch (e: Exception) {
            LOG.warn("Failed to load breadcrumbs", e)
        }
    }

    fun saveBreadcrumbs() {
        try {
            val file = getStoreFile()
            file.writeText(BreadcrumbJson.encode(breadcrumbs.toList()))
            // Let the VFS (and VCS) see the change without waiting for the next refresh.
            LocalFileSystem.getInstance().refreshIoFiles(listOf(file), true, false, null)
            LOG.info("Saved ${breadcrumbs.size} breadcrumbs to ${file.name}")
        } catch (e: Exception) {
            LOG.warn("Failed to save breadcrumbs", e)
        }
    }

    fun getAllBreadcrumbs(): List<Breadcrumb> = breadcrumbs.toList()

    fun getBreadcrumbsForFile(filePath: String): List<Breadcrumb> {
        return breadcrumbs.filter { it.filePath == filePath }
    }

    fun getBreadcrumbsByCategory(category: BreadcrumbCategory): List<Breadcrumb> {
        return breadcrumbs.filter { it.category == category }
    }

    fun findBreadcrumbById(id: String): Breadcrumb? {
        return breadcrumbs.find { it.id == id }
    }

    fun addBreadcrumb(breadcrumb: Breadcrumb) {
        breadcrumbs.add(breadcrumb)
        saveBreadcrumbs()
        notifyListeners()
    }

    fun removeBreadcrumb(id: String) {
        breadcrumbs.removeAll { it.id == id }
        saveBreadcrumbs()
        notifyListeners()
    }

    fun updateBreadcrumb(updated: Breadcrumb) {
        val index = breadcrumbs.indexOfFirst { it.id == updated.id }
        if (index >= 0) {
            breadcrumbs[index] = updated
            saveBreadcrumbs()
            notifyListeners()
        }
    }

    fun getBreadcrumbCount(): Int = breadcrumbs.size

    fun searchBreadcrumbs(query: String): List<Breadcrumb> {
        return BreadcrumbQueries.search(breadcrumbs.toList(), query)
    }

    fun getLinkedBreadcrumbs(breadcrumb: Breadcrumb): List<Breadcrumb> {
        return breadcrumb.linkedBreadcrumbs.mapNotNull { findBreadcrumbById(it) }
    }

    private fun getStoreFile(): File {
        val basePath = checkNotNull(project.basePath) { "Project base path is null" }
        return File(basePath, STORE_FILE_NAME)
    }

    private fun notifyListeners() {
        listeners.forEach { it.onBreadcrumbsChanged() }
    }

    override fun dispose() {
        listeners.clear()
        LOG.info("BreadcrumbStore disposed")
    }

    companion object {
        private val LOG = Logger.getInstance(BreadcrumbStore::class.java)
        const val STORE_FILE_NAME = ".arch-breadcrumbs.json"

        fun getInstance(project: Project): BreadcrumbStore =
            project.getService(BreadcrumbStore::class.java)
    }
}
