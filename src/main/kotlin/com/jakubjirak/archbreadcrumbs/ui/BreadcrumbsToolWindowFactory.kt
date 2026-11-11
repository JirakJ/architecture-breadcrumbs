package com.jakubjirak.archbreadcrumbs.ui

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.SearchTextField
import com.intellij.ui.components.JBScrollPane
import com.intellij.ui.content.ContentFactory
import com.intellij.ui.table.JBTable
import com.intellij.util.ui.JBUI
import com.jakubjirak.archbreadcrumbs.model.Breadcrumb
import com.jakubjirak.archbreadcrumbs.model.BreadcrumbCategory
import com.jakubjirak.archbreadcrumbs.store.BreadcrumbQueries
import com.jakubjirak.archbreadcrumbs.store.BreadcrumbStore
import java.awt.BorderLayout
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.JComboBox
import javax.swing.JPanel
import javax.swing.table.DefaultTableModel

class BreadcrumbsToolWindowFactory : ToolWindowFactory {

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val panel = BreadcrumbsToolWindowPanel(project)
        val content = ContentFactory.getInstance().createContent(panel, "", false)
        toolWindow.contentManager.addContent(content)
    }

    companion object {
        private val LOG = Logger.getInstance(BreadcrumbsToolWindowFactory::class.java)

        init {
            LOG.info("BreadcrumbsToolWindowFactory initialized")
        }
    }
}

class BreadcrumbsToolWindowPanel(private val project: Project) : JPanel(BorderLayout()) {

    private val columnNames = arrayOf("Category", "Text", "File", "Line", "Author")
    private val tableModel = object : DefaultTableModel(columnNames, 0) {
        override fun isCellEditable(row: Int, column: Int): Boolean = false
    }
    private val table = JBTable(tableModel)
    private val searchField = SearchTextField()
    private val categoryFilter = JComboBox<String>()
    private var displayedBreadcrumbs: List<Breadcrumb> = emptyList()

    init {
        setupUI()
        loadBreadcrumbs()

        val store = BreadcrumbStore.getInstance(project)
        // Store changes may come from any thread; the table must only be touched on the EDT.
        store.addListener { ApplicationManager.getApplication().invokeLater { loadBreadcrumbs() } }
    }

    private fun setupUI() {
        // Filter bar
        categoryFilter.addItem("All Categories")
        BreadcrumbCategory.entries.forEach { categoryFilter.addItem(it.displayName) }
        categoryFilter.addActionListener { loadBreadcrumbs() }

        searchField.addDocumentListener(object : com.intellij.ui.DocumentAdapter() {
            override fun textChanged(e: javax.swing.event.DocumentEvent) {
                loadBreadcrumbs()
            }
        })

        val filterPanel = JPanel(BorderLayout()).apply {
            border = JBUI.Borders.empty(4)
            add(categoryFilter, BorderLayout.WEST)
            add(searchField, BorderLayout.CENTER)
        }
        add(filterPanel, BorderLayout.NORTH)

        // Table
        table.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (e.clickCount == 2) {
                    navigateToSelectedBreadcrumb()
                }
            }
        })
        add(JBScrollPane(table), BorderLayout.CENTER)

        table.emptyText.text = "No breadcrumbs found. Use Ctrl+Shift+B to add one."
    }

    private fun loadBreadcrumbs() {
        val selectedName = categoryFilter.selectedItem as? String
        val category = BreadcrumbCategory.entries.firstOrNull { it.displayName == selectedName }
        val breadcrumbs = BreadcrumbQueries.filter(
            BreadcrumbStore.getInstance(project).getAllBreadcrumbs(),
            category,
            searchField.text.orEmpty(),
        )

        displayedBreadcrumbs = breadcrumbs
        tableModel.rowCount = 0
        breadcrumbs.forEach { b ->
            tableModel.addRow(
                arrayOf<Any>(
                    "${b.category.icon} ${b.category.displayName}",
                    b.text,
                    b.filePath,
                    b.lineNumber,
                    b.author,
                ),
            )
        }
    }

    private fun navigateToSelectedBreadcrumb() {
        val selectedRow = table.selectedRow
        if (selectedRow < 0 || selectedRow >= displayedBreadcrumbs.size) return

        val breadcrumb = displayedBreadcrumbs[selectedRow]
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
}
