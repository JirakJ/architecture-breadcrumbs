package com.jakubjirak.archbreadcrumbs.gutter

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.daemon.LineMarkerProvider
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.intellij.psi.PsiElement
import com.jakubjirak.archbreadcrumbs.settings.BreadcrumbsSettings
import com.jakubjirak.archbreadcrumbs.store.BreadcrumbQueries
import com.jakubjirak.archbreadcrumbs.store.BreadcrumbStore

/**
 * Shows one gutter icon per stored breadcrumb, anchored on the first non-whitespace leaf of its line.
 */
class BreadcrumbLineMarkerProvider : LineMarkerProvider {

    @Suppress("ReturnCount")
    override fun getLineMarkerInfo(element: PsiElement): LineMarkerInfo<*>? {
        if (element.firstChild != null) return null
        val settings = BreadcrumbsSettings.getInstance()
        if (!settings.enabled || !settings.showGutterIcons) return null

        val psiFile = element.containingFile ?: return null
        val virtualFile = psiFile.virtualFile ?: return null
        val basePath = element.project.basePath ?: return null
        val document = psiFile.viewProvider.document ?: return null

        val offset = element.textRange.startOffset
        val lineIndex = document.getLineNumber(offset)
        val anchor = firstNonWhitespaceOffset(
            document.charsSequence,
            document.getLineStartOffset(lineIndex),
            document.getLineEndOffset(lineIndex),
        )
        if (anchor != offset) return null

        val filePath = BreadcrumbQueries.relativePath(basePath, virtualFile.path)
        val breadcrumb = BreadcrumbStore.getInstance(element.project)
            .getBreadcrumbsForFile(filePath)
            .firstOrNull { it.lineNumber == lineIndex + 1 && settings.isCategoryEnabled(it.category) }
            ?: return null

        val tooltip = "${breadcrumb.category.icon} ${breadcrumb.category.displayName}: ${breadcrumb.text}"
        return LineMarkerInfo(
            element,
            element.textRange,
            BreadcrumbIcons.forCategory(breadcrumb.category),
            { tooltip },
            null,
            GutterIconRenderer.Alignment.LEFT,
            { "${breadcrumb.category.displayName}: ${breadcrumb.text}" },
        )
    }

    companion object {
        /** Offset of the first non-whitespace character in `[start, end)`, or `-1` for a blank line. */
        internal fun firstNonWhitespaceOffset(text: CharSequence, start: Int, end: Int): Int {
            for (i in start until end) {
                if (!text[i].isWhitespace()) return i
            }
            return -1
        }
    }
}
