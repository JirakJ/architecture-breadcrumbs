package com.jakubjirak.archbreadcrumbs.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBTextField
import com.intellij.util.ui.FormBuilder
import com.intellij.util.ui.JBUI
import com.jakubjirak.archbreadcrumbs.licensing.FreemiumGate
import com.jakubjirak.archbreadcrumbs.model.Breadcrumb
import com.jakubjirak.archbreadcrumbs.model.BreadcrumbCategory
import com.jakubjirak.archbreadcrumbs.settings.BreadcrumbsSettings
import com.jakubjirak.archbreadcrumbs.store.BreadcrumbStore
import javax.swing.JComboBox
import javax.swing.JComponent
import javax.swing.JPanel

class AddBreadcrumbAction : AnAction() {

    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val editor = e.getData(CommonDataKeys.EDITOR) ?: return
        val virtualFile = e.getData(CommonDataKeys.VIRTUAL_FILE) ?: return

        val store = BreadcrumbStore.getInstance(project)
        val gate = FreemiumGate.getInstance()

        if (!gate.canAddBreadcrumb(store.getBreadcrumbCount())) {
            com.intellij.notification.NotificationGroupManager.getInstance()
                .getNotificationGroup("Architecture Breadcrumbs")
                .createNotification(
                    "Free tier limit reached (${FreemiumGate.MAX_FREE_BREADCRUMBS} breadcrumbs). Upgrade for unlimited.",
                    com.intellij.notification.NotificationType.WARNING,
                )
                .notify(project)
            return
        }

        val document = editor.document
        val lineNumber = document.getLineNumber(editor.caretModel.offset) + 1

        val basePath = project.basePath ?: return
        val filePath = virtualFile.path.let {
            if (it.startsWith(basePath)) it.removePrefix(basePath).removePrefix("/") else it
        }

        val dialog = AddBreadcrumbDialog(project)
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

class AddBreadcrumbDialog(
    @Suppress("UNUSED_PARAMETER") project: com.intellij.openapi.project.Project,
) : DialogWrapper(true) {

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

    fun getLinkedIds(): List<String> {
        val text = linkedField.text?.trim() ?: ""
        if (text.isEmpty()) return emptyList()
        return text.split(",").map { it.trim() }.filter { it.isNotEmpty() }
    }

    override fun doOKAction() {
        if (getBreadcrumbText().isEmpty()) return
        super.doOKAction()
    }
}
