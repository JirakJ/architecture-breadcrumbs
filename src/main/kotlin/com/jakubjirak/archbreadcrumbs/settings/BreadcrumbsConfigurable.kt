package com.jakubjirak.archbreadcrumbs.settings

import com.intellij.openapi.options.Configurable
import com.intellij.ui.IdeBorderFactory
import com.intellij.ui.components.JBCheckBox
import com.intellij.util.ui.FormBuilder
import javax.swing.JComponent
import javax.swing.JPanel

class BreadcrumbsConfigurable : Configurable {

    private var enabledCheckbox: JBCheckBox? = null
    private var gutterIconsCheckbox: JBCheckBox? = null
    private var decisionCheckbox: JBCheckBox? = null
    private var patternCheckbox: JBCheckBox? = null
    private var constraintCheckbox: JBCheckBox? = null
    private var todoCheckbox: JBCheckBox? = null
    private var warningCheckbox: JBCheckBox? = null
    private var contextCheckbox: JBCheckBox? = null
    private var mainPanel: JPanel? = null

    override fun getDisplayName(): String = "Architecture Breadcrumbs"

    override fun createComponent(): JComponent {
        val settings = BreadcrumbsSettings.getInstance()

        enabledCheckbox = JBCheckBox("Enable Architecture Breadcrumbs", settings.enabled)
        gutterIconsCheckbox = JBCheckBox("Show gutter icons", settings.showGutterIcons)

        decisionCheckbox = JBCheckBox("Decision", settings.enableDecision)
        patternCheckbox = JBCheckBox("Pattern", settings.enablePattern)
        constraintCheckbox = JBCheckBox("Constraint", settings.enableConstraint)
        todoCheckbox = JBCheckBox("TODO (Architecture)", settings.enableTodoArch)
        warningCheckbox = JBCheckBox("Warning", settings.enableWarning)
        contextCheckbox = JBCheckBox("Context", settings.enableContext)

        val categoriesPanel = FormBuilder.createFormBuilder()
            .addComponent(decisionCheckbox!!)
            .addComponent(patternCheckbox!!)
            .addComponent(constraintCheckbox!!)
            .addComponent(todoCheckbox!!)
            .addComponent(warningCheckbox!!)
            .addComponent(contextCheckbox!!)
            .panel
            .apply {
                border = IdeBorderFactory.createTitledBorder("Enabled Categories")
            }

        mainPanel = FormBuilder.createFormBuilder()
            .addComponent(enabledCheckbox!!)
            .addComponent(gutterIconsCheckbox!!)
            .addComponent(categoriesPanel)
            .addComponentFillVertically(JPanel(), 0)
            .panel

        return mainPanel!!
    }

    override fun isModified(): Boolean {
        val settings = BreadcrumbsSettings.getInstance()
        return enabledCheckbox?.isSelected != settings.enabled ||
            gutterIconsCheckbox?.isSelected != settings.showGutterIcons ||
            decisionCheckbox?.isSelected != settings.enableDecision ||
            patternCheckbox?.isSelected != settings.enablePattern ||
            constraintCheckbox?.isSelected != settings.enableConstraint ||
            todoCheckbox?.isSelected != settings.enableTodoArch ||
            warningCheckbox?.isSelected != settings.enableWarning ||
            contextCheckbox?.isSelected != settings.enableContext
    }

    override fun apply() {
        val settings = BreadcrumbsSettings.getInstance()
        settings.enabled = enabledCheckbox?.isSelected ?: true
        settings.showGutterIcons = gutterIconsCheckbox?.isSelected ?: true
        settings.enableDecision = decisionCheckbox?.isSelected ?: true
        settings.enablePattern = patternCheckbox?.isSelected ?: true
        settings.enableConstraint = constraintCheckbox?.isSelected ?: true
        settings.enableTodoArch = todoCheckbox?.isSelected ?: true
        settings.enableWarning = warningCheckbox?.isSelected ?: true
        settings.enableContext = contextCheckbox?.isSelected ?: true
    }

    override fun reset() {
        val settings = BreadcrumbsSettings.getInstance()
        enabledCheckbox?.isSelected = settings.enabled
        gutterIconsCheckbox?.isSelected = settings.showGutterIcons
        decisionCheckbox?.isSelected = settings.enableDecision
        patternCheckbox?.isSelected = settings.enablePattern
        constraintCheckbox?.isSelected = settings.enableConstraint
        todoCheckbox?.isSelected = settings.enableTodoArch
        warningCheckbox?.isSelected = settings.enableWarning
        contextCheckbox?.isSelected = settings.enableContext
    }

    override fun disposeUIResources() {
        enabledCheckbox = null
        gutterIconsCheckbox = null
        decisionCheckbox = null
        patternCheckbox = null
        constraintCheckbox = null
        todoCheckbox = null
        warningCheckbox = null
        contextCheckbox = null
        mainPanel = null
    }
}
