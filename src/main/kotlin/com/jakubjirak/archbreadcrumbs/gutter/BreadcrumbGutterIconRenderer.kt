package com.jakubjirak.archbreadcrumbs.gutter

import com.intellij.icons.AllIcons
import com.intellij.openapi.editor.markup.GutterIconRenderer
import com.jakubjirak.archbreadcrumbs.model.Breadcrumb
import com.jakubjirak.archbreadcrumbs.model.BreadcrumbCategory
import javax.swing.Icon

class BreadcrumbGutterIconRenderer(
    private val breadcrumb: Breadcrumb,
) : GutterIconRenderer() {

    override fun getIcon(): Icon = getIconForCategory(breadcrumb.category)

    override fun getTooltipText(): String {
        return "${breadcrumb.category.icon} ${breadcrumb.category.displayName}: ${breadcrumb.text}"
    }

    override fun getAlignment(): Alignment = Alignment.LEFT

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BreadcrumbGutterIconRenderer) return false
        return breadcrumb.id == other.breadcrumb.id
    }

    override fun hashCode(): Int = breadcrumb.id.hashCode()

    companion object {
        fun getIconForCategory(category: BreadcrumbCategory): Icon = when (category) {
            BreadcrumbCategory.DECISION -> AllIcons.Actions.IntentionBulb
            BreadcrumbCategory.PATTERN -> AllIcons.Nodes.Plugin
            BreadcrumbCategory.CONSTRAINT -> AllIcons.Nodes.Locked
            BreadcrumbCategory.TODO_ARCH -> AllIcons.General.TodoDefault
            BreadcrumbCategory.WARNING -> AllIcons.General.Warning
            BreadcrumbCategory.CONTEXT -> AllIcons.General.Information
        }
    }
}
