package com.jakubjirak.archbreadcrumbs.git

import com.jakubjirak.archbreadcrumbs.model.Breadcrumb
import com.jakubjirak.archbreadcrumbs.model.BreadcrumbCategory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BranchImpactAnalyzerTest {

    private val analyzer = BranchImpactAnalyzer()

    @Test
    fun `no impact when both branches have same breadcrumbs`() {
        val breadcrumbs = listOf(
            createBreadcrumb("b1", "Pattern A"),
            createBreadcrumb("b2", "Decision B"),
        )

        val impact = analyzer.analyze("feature/x", "main", breadcrumbs, breadcrumbs)

        assertFalse(analyzer.hasImpact(impact))
        assertEquals("No architectural changes", analyzer.impactSummary(impact))
    }

    @Test
    fun `detects added breadcrumbs`() {
        val source = listOf(
            createBreadcrumb("b1", "Existing"),
            createBreadcrumb("b2", "New in branch"),
        )
        val target = listOf(
            createBreadcrumb("b1", "Existing"),
        )

        val impact = analyzer.analyze("feature/x", "main", source, target)

        assertEquals(1, impact.addedBreadcrumbs.size)
        assertEquals("b2", impact.addedBreadcrumbs[0].id)
        assertTrue(analyzer.hasImpact(impact))
    }

    @Test
    fun `detects removed breadcrumbs`() {
        val source = listOf(
            createBreadcrumb("b1", "Kept"),
        )
        val target = listOf(
            createBreadcrumb("b1", "Kept"),
            createBreadcrumb("b2", "Removed from branch"),
        )

        val impact = analyzer.analyze("feature/x", "main", source, target)

        assertEquals(1, impact.removedBreadcrumbs.size)
        assertEquals("b2", impact.removedBreadcrumbs[0].id)
    }

    @Test
    fun `detects modified breadcrumbs`() {
        val source = listOf(
            createBreadcrumb("b1", "Updated text"),
        )
        val target = listOf(
            createBreadcrumb("b1", "Original text"),
        )

        val impact = analyzer.analyze("feature/x", "main", source, target)

        assertEquals(1, impact.modifiedBreadcrumbs.size)
        assertEquals("b1", impact.modifiedBreadcrumbs[0].id)
    }

    @Test
    fun `impact summary describes all changes`() {
        val source = listOf(
            createBreadcrumb("b1", "Modified"),
            createBreadcrumb("b3", "Added"),
        )
        val target = listOf(
            createBreadcrumb("b1", "Original"),
            createBreadcrumb("b2", "Removed"),
        )

        val impact = analyzer.analyze("feature/x", "main", source, target)
        val summary = analyzer.impactSummary(impact)

        assertTrue(summary.contains("1 added"))
        assertTrue(summary.contains("1 removed"))
        assertTrue(summary.contains("1 modified"))
    }

    @Test
    fun `branch names are preserved in impact`() {
        val impact = analyzer.analyze("feature/x", "main", emptyList(), emptyList())

        assertEquals("feature/x", impact.branchA)
        assertEquals("main", impact.branchB)
    }

    private fun createBreadcrumb(id: String, text: String): Breadcrumb = Breadcrumb(
        id = id,
        text = text,
        category = BreadcrumbCategory.DECISION,
        author = "test",
        timestamp = System.currentTimeMillis(),
        filePath = "src/main/kotlin/Test.kt",
        lineNumber = 10,
    )
}
