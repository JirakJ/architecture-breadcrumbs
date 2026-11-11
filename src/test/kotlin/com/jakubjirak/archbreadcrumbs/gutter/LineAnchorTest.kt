package com.jakubjirak.archbreadcrumbs.gutter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LineAnchorTest {

    private val text = "fun a() {\n    val x = 1\n   \n}"

    @Test
    fun `anchor skips leading indentation`() {
        assertEquals(14, BreadcrumbLineMarkerProvider.firstNonWhitespaceOffset(text, 10, 23))
    }

    @Test
    fun `anchor at column zero`() {
        assertEquals(0, BreadcrumbLineMarkerProvider.firstNonWhitespaceOffset(text, 0, 9))
    }

    @Test
    fun `blank line has no anchor`() {
        assertEquals(-1, BreadcrumbLineMarkerProvider.firstNonWhitespaceOffset(text, 24, 27))
        assertEquals(-1, BreadcrumbLineMarkerProvider.firstNonWhitespaceOffset("", 0, 0))
    }
}
