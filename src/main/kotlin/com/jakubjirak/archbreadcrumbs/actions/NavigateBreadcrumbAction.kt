package com.jakubjirak.archbreadcrumbs.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.ui.popup.JBPopupFactory
import com.jakubjirak.archbreadcrumbs.model.Breadcrumb
import com.jakubjirak.archbreadcrumbs.settings.BreadcrumbsSettings
import com.jakubjirak.archbreadcrumbs.store.BreadcrumbQueries
import com.jakubjirak.archbreadcrumbs.store.BreadcrumbStore

class NavigateBreadcrumbAction : AnAction() {
    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT


    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        val store = BreadcrumbStore.getInstance(project)
        val basePath = project.basePath ?: return
        val filePath = BreadcrumbQueries.relativePath(basePath, virtualFile.path)

        val lineNumber = editor.document.getLineNumber(editor.caretModel.offset) + 1
        val currentBreadcrumb = store.getBreadcrumbsForFile(filePath).find { it.lineNumber == lineNumber }

        handleNavigation(project, store, currentBreadcrumb)
    }

    private fun handleNavigation(
        project: com.intellij.openapi.project.Project,
        store: BreadcrumbStore,
        currentBreadcrumb: Breadcrumb?,
    ) {
        if (currentBreadcrumb == null) {
            showAllBreadcrumbsPopup(project, store)
            return
        }

        val linked = store.getLinkedBreadcrumbs(currentBreadcrumb)
        if (linked.isEmpty()) {
            showAllBreadcrumbsPopup(project, store)
            return
        }

        showLinkedBreadcrumbsPopup(project, linked)
    }

    private fun showAllBreadcrumbsPopup(project: com.intellij.openapi.project.Project, store: BreadcrumbStore) {
        val allBreadcrumbs = store.getAllBreadcrumbs()
        if (allBreadcrumbs.isEmpty()) return

        val items = allBreadcrumbs.map { "${it.category.icon} ${it.text} (${it.filePath}:${it.lineNumber})" }
        JBPopupFactory.getInstance()
            .createPopupChooserBuilder(items)
            .setTitle("Architecture Breadcrumbs")
            .setItemChosenCallback { selected ->
                val index = items.indexOf(selected)
                if (index >= 0) navigateToBreadcrumb(project, allBreadcrumbs[index])
            }
            .createPopup()
            .showCenteredInCurrentWindow(project)
    }

    private fun showLinkedBreadcrumbsPopup(
        project: com.intellij.openapi.project.Project,
        linked: List<Breadcrumb>,
    ) {
        val items = linked.map { "${it.category.icon} ${it.text} (${it.filePath}:${it.lineNumber})" }
        JBPopupFactory.getInstance()
            .createPopupChooserBuilder(items)
            .setTitle("Linked Breadcrumbs")
            .setItemChosenCallback { selected ->
                val index = items.indexOf(selected)
                if (index >= 0) navigateToBreadcrumb(project, linked[index])
            }
            .createPopup()
            .showCenteredInCurrentWindow(project)
    }

    private fun navigateToBreadcrumb(project: com.intellij.openapi.project.Project, breadcrumb: Breadcrumb) {
        val basePath = project.basePath ?: return
        val file = com.intellij.openapi.vfs.LocalFileSystem.getInstance()
            .findFileByPath("$basePath/${breadcrumb.filePath}") ?: return

        com.intellij.openapi.fileEditor.OpenFileDescriptor(
            project,
            file,
            breadcrumb.lineNumber - 1,
            0,
        ).navigate(true)
    }

    override fun update(e: AnActionEvent) {
        val settings = BreadcrumbsSettings.getInstance()
        e.presentation.isEnabledAndVisible = settings.enabled && e.project != null
    }
}
