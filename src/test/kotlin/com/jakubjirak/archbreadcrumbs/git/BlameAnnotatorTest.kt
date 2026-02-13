package com.jakubjirak.archbreadcrumbs.git

import com.jakubjirak.archbreadcrumbs.model.Breadcrumb
import com.jakubjirak.archbreadcrumbs.model.BreadcrumbCategory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.Instant
import java.time.temporal.ChronoUnit

class BlameAnnotatorTest {

    private val annotator = BlameAnnotator()

    @Test
    fun `breadcrumb is not stale when code has not changed`() {
        val breadcrumbDate = Instant.now().minus(10, ChronoUnit.DAYS)
        assertFalse(annotator.isStale(breadcrumbDate, null))
    }

    @Test
    fun `breadcrumb is not stale when code changed recently`() {
        val breadcrumbDate = Instant.now().minus(10, ChronoUnit.DAYS)
        val codeChangeDate = Instant.now().minus(5, ChronoUnit.DAYS)
        assertFalse(annotator.isStale(breadcrumbDate, codeChangeDate))
    }

    @Test
    fun `breadcrumb is stale when code changed long after breadcrumb`() {
        val breadcrumbDate = Instant.now().minus(60, ChronoUnit.DAYS)
        val codeChangeDate = Instant.now().minus(5, ChronoUnit.DAYS)
        assertTrue(annotator.isStale(breadcrumbDate, codeChangeDate))
    }

    @Test
    fun `staleDays returns null when not stale`() {
        val breadcrumbDate = Instant.now().minus(10, ChronoUnit.DAYS)
        val codeChangeDate = Instant.now().minus(5, ChronoUnit.DAYS)
        assertNull(annotator.staleDays(breadcrumbDate, codeChangeDate))
    }

    @Test
    fun `staleDays returns days when stale`() {
        val breadcrumbDate = Instant.now().minus(60, ChronoUnit.DAYS)
        val codeChangeDate = Instant.now().minus(5, ChronoUnit.DAYS)
        val days = annotator.staleDays(breadcrumbDate, codeChangeDate)
        assertNotNull(days)
        assertTrue(days!! > 30)
    }

    @Test
    fun `annotate creates BlameAnnotatedBreadcrumb`() {
        val breadcrumb = createBreadcrumb("test-1")
        val blameInfo = BlameInfo("alice", Instant.now().minus(60, ChronoUnit.DAYS), "abc123")
        val codeChangeDate = Instant.now().minus(5, ChronoUnit.DAYS)

        val annotated = annotator.annotate(breadcrumb, blameInfo, codeChangeDate)

        assertEquals(breadcrumb, annotated.breadcrumb)
        assertEquals("alice", annotated.blameInfo.author)
        assertTrue(annotated.isStale)
        assertNotNull(annotated.staleDays)
    }

    @Test
    fun `annotate marks non-stale breadcrumb correctly`() {
        val breadcrumb = createBreadcrumb("test-2")
        val blameInfo = BlameInfo("bob", Instant.now().minus(5, ChronoUnit.DAYS), "def456")

        val annotated = annotator.annotate(breadcrumb, blameInfo, null)

        assertFalse(annotated.isStale)
        assertNull(annotated.staleDays)
    }

    @Test
    fun `custom stale threshold is respected`() {
        val customAnnotator = BlameAnnotator(staleThresholdDays = 10)
        val breadcrumbDate = Instant.now().minus(15, ChronoUnit.DAYS)
        val codeChangeDate = Instant.now().minus(1, ChronoUnit.DAYS)

        assertTrue(customAnnotator.isStale(breadcrumbDate, codeChangeDate))
    }

    private fun createBreadcrumb(id: String): Breadcrumb = Breadcrumb(
        id = id,
        text = "Test breadcrumb $id",
        category = BreadcrumbCategory.DECISION,
        author = "test",
        timestamp = System.currentTimeMillis(),
        filePath = "src/main/kotlin/Test.kt",
        lineNumber = 10,
    )
}
