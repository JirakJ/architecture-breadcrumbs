package com.jakubjirak.archbreadcrumbs.settings

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.PersistentStateComponent
import com.intellij.openapi.components.RoamingType
import com.intellij.openapi.components.State
import com.intellij.openapi.components.Storage
import com.intellij.util.xmlb.XmlSerializerUtil
import com.jakubjirak.archbreadcrumbs.model.BreadcrumbCategory

@State(
    name = "BreadcrumbsSettings",
    storages = [Storage(value = "archBreadcrumbsSettings.xml", roamingType = RoamingType.DEFAULT)],
)
class BreadcrumbsSettings : PersistentStateComponent<BreadcrumbsSettings> {

    var enabled: Boolean = true
    var showGutterIcons: Boolean = true

    var enableDecision: Boolean = true
    var enablePattern: Boolean = true
    var enableConstraint: Boolean = true
    var enableTodoArch: Boolean = true
    var enableWarning: Boolean = true
    var enableContext: Boolean = true

    fun isCategoryEnabled(category: BreadcrumbCategory): Boolean = when (category) {
        BreadcrumbCategory.DECISION -> enableDecision
        BreadcrumbCategory.PATTERN -> enablePattern
        BreadcrumbCategory.CONSTRAINT -> enableConstraint
        BreadcrumbCategory.TODO_ARCH -> enableTodoArch
        BreadcrumbCategory.WARNING -> enableWarning
        BreadcrumbCategory.CONTEXT -> enableContext
    }

    override fun getState(): BreadcrumbsSettings = this

    override fun loadState(state: BreadcrumbsSettings) {
        XmlSerializerUtil.copyBean(state, this)
    }

    companion object {
        fun getInstance(): BreadcrumbsSettings =
            ApplicationManager.getApplication().getService(BreadcrumbsSettings::class.java)
    }
}
