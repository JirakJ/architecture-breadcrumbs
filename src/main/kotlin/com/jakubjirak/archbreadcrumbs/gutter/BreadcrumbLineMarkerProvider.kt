package com.jakubjirak.archbreadcrumbs.gutter

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.jakubjirak.archbreadcrumbs.model.BreadcrumbCategory
import com.jakubjirak.archbreadcrumbs.settings.BreadcrumbsSettings
import com.jakubjirak.archbreadcrumbs.store.BreadcrumbStore

class BreadcrumbLineMarkerProvider : LineMarkerProvider {

    @Suppress("ReturnCount")
    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<*>? {
        val settings = BreadcrumbsSettings.getInstance()
        if (!settings.enabled || !settings.showGutterIcons) return null

        val psiFile = element.containingFile ?: return null
        if (element != psiFile.findElementAt(element.textOffset)) return null

        val project = element.project
        val store = BreadcrumbStore.getInstance(project)
        val filePath = getRelativePath(project, psiFile) ?: return null
        val document = psiFile.viewProvider.document ?: return null
        val lineNumber = document.getLineNumber(element.textOffset) + 1

        val breadcrumb = store.getBreadcrumbsForFile(filePath).find { it.lineNumber == lineNumber } ?: return null
        if (!isCategoryEnabled(settings, breadcrumb.category)) return null

        LOG.debug("Line marker found for breadcrumb ${breadcrumb.id} at $filePath:$lineNumber")
        return LineMarkerInfo(
            element,
            element.textRange,
            BreadcrumbGutterIconRenderer.getIconForCategory(breadcrumb.category),
            { "${breadcrumb.category.icon} ${breadcrumb.category.displayName}: ${breadcrumb.text}" },
            null,
            GutterIconRenderer.Alignment.LEFT,
            { "${breadcrumb.category.displayName}: ${breadcrumb.text}" },
        )
    }

    companion object {
        private val LOG = Logger.getInstance(BreadcrumbLineMarkerProvider::class.java)

        private fun getRelativePath(project: com.intellij.openapi.project.Project, psiFile: PsiFile): String? {
            val virtualFile = psiFile.virtualFile ?: return null
            val basePath = project.basePath ?: return null
            val filePath = virtualFile.path
            return if (filePath.startsWith(basePath)) {
                filePath.removePrefix(basePath).removePrefix("/")
            } else {
                filePath
            }
        }

        private fun isCategoryEnabled(settings: BreadcrumbsSettings, category: BreadcrumbCategory): Boolean {
            return when (category) {
                BreadcrumbCategory.DECISION -> settings.enableDecision
                BreadcrumbCategory.PATTERN -> settings.enablePattern
                BreadcrumbCategory.CONSTRAINT -> settings.enableConstraint
                BreadcrumbCategory.TODO_ARCH -> settings.enableTodoArch
                BreadcrumbCategory.WARNING -> settings.enableWarning
                BreadcrumbCategory.CONTEXT -> settings.enableContext
            }
        }
    }
}
