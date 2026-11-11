package com.jakubjirak.archbreadcrumbs.settings

import com.jakubjirak.archbreadcrumbs.model.BreadcrumbCategory
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BreadcrumbsSettingsTest {

    @Test
    fun `all categories enabled by default`() {
        val settings = BreadcrumbsSettings()
        assertTrue(settings.enabled)
        assertTrue(settings.showGutterIcons)
        BreadcrumbCategory.entries.forEach { assertTrue(settings.isCategoryEnabled(it), it.name) }
    }

    @Test
    fun `each category toggle maps to its own category`() {
        val toggles = mapOf<BreadcrumbCategory, (BreadcrumbsSettings) -> Unit>(
            BreadcrumbCategory.DECISION to { it.enableDecision = false },
            BreadcrumbCategory.PATTERN to { it.enablePattern = false },
            BreadcrumbCategory.CONSTRAINT to { it.enableConstraint = false },
            BreadcrumbCategory.TODO_ARCH to { it.enableTodoArch = false },
            BreadcrumbCategory.WARNING to { it.enableWarning = false },
            BreadcrumbCategory.CONTEXT to { it.enableContext = false },
        )
        assertTrue(toggles.keys == BreadcrumbCategory.entries.toSet())
        toggles.forEach { (category, disable) ->
            val settings = BreadcrumbsSettings().also(disable)
            BreadcrumbCategory.entries.forEach {
                if (it == category) assertFalse(settings.isCategoryEnabled(it)) else assertTrue(settings.isCategoryEnabled(it))
            }
        }
    }

    @Test
    fun `state is the settings bean itself`() {
        val settings = BreadcrumbsSettings()
        assertSame(settings, settings.getState())
    }
}
