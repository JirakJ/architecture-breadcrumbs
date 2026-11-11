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
                    .comment("Turns gutter icons and the add/navigate actions on or off")
            }
            row {
                checkBox("Show gutter icons")
                    .bindSelected(settings::showGutterIcons)
                    .comment("Display icons in the editor gutter for each breadcrumb")
            }
        }

        group("Gutter Icons per Category") {
            row {
                checkBox("Decision")
                    .bindSelected(settings::enableDecision)
                    .comment("Show gutter icons for Decision breadcrumbs")
            }
            row {
                checkBox("Pattern")
                    .bindSelected(settings::enablePattern)
                    .comment("Show gutter icons for Pattern breadcrumbs")
            }
            row {
                checkBox("Constraint")
                    .bindSelected(settings::enableConstraint)
                    .comment("Show gutter icons for Constraint breadcrumbs")
            }
            row {
                checkBox("TODO (Architecture)")
                    .bindSelected(settings::enableTodoArch)
                    .comment("Show gutter icons for architecture TODO breadcrumbs")
            }
            row {
                checkBox("Warning")
                    .bindSelected(settings::enableWarning)
                    .comment("Show gutter icons for Warning breadcrumbs")
            }
            row {
                checkBox("Context")
                    .bindSelected(settings::enableContext)
                    .comment("Show gutter icons for Context breadcrumbs")
            }
        }
    }
}
