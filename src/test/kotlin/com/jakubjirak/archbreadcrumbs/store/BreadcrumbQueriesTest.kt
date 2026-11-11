package com.jakubjirak.archbreadcrumbs.store

import com.jakubjirak.archbreadcrumbs.model.Breadcrumb
import com.jakubjirak.archbreadcrumbs.model.BreadcrumbCategory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BreadcrumbQueriesTest {

    private val a = Breadcrumb(id = "a", text = "Use Repository", category = BreadcrumbCategory.PATTERN,
        author = "Alice", filePath = "src/Repo.kt", lineNumber = 1)
    private val b = Breadcrumb(id = "b", text = "No DB in controllers", category = BreadcrumbCategory.CONSTRAINT,
        author = "bob", filePath = "src/Ctrl.kt", lineNumber = 2)
    private val all = listOf(a, b)

    @Test
    fun `search is case-insensitive across text, author, path and category`() {
        assertEquals(listOf(a), BreadcrumbQueries.search(all, "repository"))
        assertEquals(listOf(a), BreadcrumbQueries.search(all, "ALICE"))
        assertEquals(listOf(b), BreadcrumbQueries.search(all, "ctrl.kt"))
        assertEquals(listOf(b), BreadcrumbQueries.search(all, "constraint"))
        assertEquals(emptyList<Breadcrumb>(), BreadcrumbQueries.search(all, "nothing"))
    }

    @Test
    fun `blank query matches everything`() {
        assertEquals(all, BreadcrumbQueries.search(all, "  "))
    }

    @Test
    fun `filter combines category and query`() {
        assertEquals(all, BreadcrumbQueries.filter(all, null, ""))
        assertEquals(listOf(b), BreadcrumbQueries.filter(all, BreadcrumbCategory.CONSTRAINT, ""))
        assertEquals(emptyList<Breadcrumb>(), BreadcrumbQueries.filter(all, BreadcrumbCategory.CONSTRAINT, "repository"))
    }

    @Test
    fun `linked ids are trimmed, de-duplicated and blanks dropped`() {
        assertEquals(listOf("x", "y"), BreadcrumbQueries.parseLinkedIds(" x, y ,,x "))
        assertEquals(emptyList<String>(), BreadcrumbQueries.parseLinkedIds(null))
        assertEquals(emptyList<String>(), BreadcrumbQueries.parseLinkedIds("  "))
    }

    @Test
    fun `relative path only strips a real directory prefix`() {
        assertEquals("src/A.kt", BreadcrumbQueries.relativePath("/proj", "/proj/src/A.kt"))
        assertEquals("src/A.kt", BreadcrumbQueries.relativePath("/proj/", "/proj/src/A.kt"))
        assertEquals("/proj2/src/A.kt", BreadcrumbQueries.relativePath("/proj", "/proj2/src/A.kt"))
        assertEquals("/elsewhere/A.kt", BreadcrumbQueries.relativePath("/proj", "/elsewhere/A.kt"))
    }
}
