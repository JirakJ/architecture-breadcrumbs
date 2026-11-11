package com.jakubjirak.archbreadcrumbs.actions

import com.intellij.codeInsight.daemon.DaemonCodeAnalyzer
import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.actionSystem.ActionUpdateThread
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.openapi.ui.ValidationInfo
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.JBUI
import com.jakubjirak.archbreadcrumbs.model.Breadcrumb
import com.jakubjirak.archbreadcrumbs.model.BreadcrumbCategory
import com.jakubjirak.archbreadcrumbs.settings.BreadcrumbsSettings
import com.jakubjirak.archbreadcrumbs.store.BreadcrumbQueries
import com.jakubjirak.archbreadcrumbs.store.BreadcrumbStore
import javax.swing.JComboBox
import javax.swing.JComponent

class AddBreadcrumbAction : AnAction() {
    override fun getActionUpdateThread(): ActionUpdateThread = ActionUpdateThread.BGT


    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        val store = BreadcrumbStore.getInstance(project)

        val document = editor.document
        val lineNumber = document.getLineNumber(editor.caretModel.offset) + 1

        val basePath = project.basePath ?: return
        val filePath = BreadcrumbQueries.relativePath(basePath, virtualFile.path)

        val dialog = AddBreadcrumbDialog()
        if (dialog.showAndGet()) {
            val linkedIds = dialog.getLinkedIds()
            val breadcrumb = Breadcrumb(
                text = dialog.getBreadcrumbText(),
                category = dialog.getCategory(),
                author = dialog.getAuthor(),
                filePath = filePath,
                lineNumber = lineNumber,
                linkedBreadcrumbs = linkedIds,
            )
            store.addBreadcrumb(breadcrumb)
            // Re-run line markers so the new gutter icon shows up immediately.
            DaemonCodeAnalyzer.getInstance(project).restart()
            LOG.info("Added breadcrumb at $filePath:$lineNumber")
        }
    }

    override fun update(e: AnActionEvent) {
        val settings = BreadcrumbsSettings.getInstance()
        e.presentation.isEnabledAndVisible = settings.enabled && e.getData(CommonDataKeys.EDITOR) != null
    }

    companion object {
        private val LOG = Logger.getInstance(AddBreadcrumbAction::class.java)
    }
}

class AddBreadcrumbDialog : DialogWrapper(true) {

    private val textField = JBTextField().apply {
        columns = 40
    }
    private val categoryCombo = JComboBox(BreadcrumbCategory.entries.toTypedArray())
    private val authorField = JBTextField(System.getProperty("user.name", "unknown")).apply {
        columns = 20
    }
    private val linkedField = JBTextField().apply {
        columns = 40
        emptyText.text = "Optional: comma-separated breadcrumb IDs"
    }

    init {
        title = "Add Architecture Breadcrumb"
        init()
    }

    override fun createCenterPanel(): JComponent {
        return FormBuilder.createFormBuilder()
            .addLabeledComponent(JBLabel("Breadcrumb text:"), textField)
            .addLabeledComponent(JBLabel("Category:"), categoryCombo)
            .addLabeledComponent(JBLabel("Author:"), authorField)
            .addLabeledComponent(JBLabel("Linked breadcrumbs:"), linkedField)
            .panel
            .apply {
                border = JBUI.Borders.empty(10)
                preferredSize = JBUI.size(500, 200)
            }
    }

    fun getBreadcrumbText(): String = textField.text.trim()

    fun getCategory(): BreadcrumbCategory = categoryCombo.selectedItem as BreadcrumbCategory

    fun getAuthor(): String = authorField.text.trim()

    fun getLinkedIds(): List<String> = BreadcrumbQueries.parseLinkedIds(linkedField.text)

    override fun doValidate(): ValidationInfo? =
        if (getBreadcrumbText().isEmpty()) ValidationInfo("Breadcrumb text must not be empty", textField) else null
}
