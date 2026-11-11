package com.jakubjirak.archbreadcrumbs.gutter

import com.intellij.icons.AllIcons
import com.jakubjirak.archbreadcrumbs.model.BreadcrumbCategory
import javax.swing.Icon

/** Gutter icon per breadcrumb category. */
object BreadcrumbIcons {
    fun forCategory(category: BreadcrumbCategory): Icon = when (category) {
        BreadcrumbCategory.DECISION -> AllIcons.Actions.IntentionBulb
        BreadcrumbCategory.PATTERN -> AllIcons.Nodes.Plugin
        BreadcrumbCategory.CONSTRAINT -> AllIcons.Nodes.Locked
        BreadcrumbCategory.TODO_ARCH -> AllIcons.General.TodoDefault
        BreadcrumbCategory.WARNING -> AllIcons.General.Warning
        BreadcrumbCategory.CONTEXT -> AllIcons.General.Information
    }
}
