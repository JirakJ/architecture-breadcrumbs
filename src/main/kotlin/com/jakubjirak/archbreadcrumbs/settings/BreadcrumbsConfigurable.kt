package com.jakubjirak.archbreadcrumbs.settings

import com.intellij.openapi.options.BoundConfigurable
import com.intellij.ui.dsl.builder.*

class BreadcrumbsConfigurable : BoundConfigurable("Architecture Breadcrumbs") {

    override fun createPanel() = panel {
        val settings = BreadcrumbsSettings.getInstance()

        group("General") {
            row {
                checkBox("Enable Architecture Breadcrumbs")
                    .bindSelected(settings::enabled)
                    .comment("Toggle all architecture breadcrumb annotations on or off")
            }
            row {
                checkBox("Show gutter icons")
                    .bindSelected(settings::showGutterIcons)
                    .comment("Display icons in the editor gutter for each breadcrumb")
            }
            row("Icon style:") {
                comboBox(listOf("Default", "Minimal", "Colorful"))
                    .bindItem(
                        { settings.iconStyle },
                        { settings.iconStyle = it ?: "Default" }
                    )
                    .comment("Visual style used for gutter icons")
            }
        }

        group("Breadcrumb Categories") {
            row {
                checkBox("Decision")
                    .bindSelected(settings::enableDecision)
                    .comment("Track architectural decisions captured in code comments")
            }
            row {
                checkBox("Pattern")
                    .bindSelected(settings::enablePattern)
                    .comment("Track design pattern annotations and usages")
            }
            row {
                checkBox("Constraint")
                    .bindSelected(settings::enableConstraint)
                    .comment("Track architectural constraints and invariants")
            }
            row {
                checkBox("TODO (Architecture)")
                    .bindSelected(settings::enableTodoArch)
                    .comment("Track architecture-related TODO items")
            }
            row {
                checkBox("Warning")
                    .bindSelected(settings::enableWarning)
                    .comment("Track architectural warnings and potential issues")
            }
            row {
                checkBox("Context")
                    .bindSelected(settings::enableContext)
                    .comment("Track contextual notes about architectural intent")
            }
        }
    }
}
