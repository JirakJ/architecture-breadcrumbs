package com.jakubjirak.archbreadcrumbs.store

import com.google.gson.JsonSyntaxException
import com.jakubjirak.archbreadcrumbs.model.Breadcrumb
import com.jakubjirak.archbreadcrumbs.model.BreadcrumbCategory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class BreadcrumbJsonTest {

    @Test
    fun `round trip preserves every field`() {
        val b = Breadcrumb(
            id = "id-1", text = "t", category = BreadcrumbCategory.WARNING, author = "a",
            timestamp = 42L, filePath = "src/A.kt", lineNumber = 7, linkedBreadcrumbs = listOf("x", "y"),
        )
        assertEquals(listOf(b), BreadcrumbJson.decode(BreadcrumbJson.encode(listOf(b))))
    }

    @Test
    fun `blank text decodes to empty list`() {
        assertTrue(BreadcrumbJson.decode("   ").isEmpty())
        assertTrue(BreadcrumbJson.decode("[]").isEmpty())
    }

    @Test
    fun `non-array or malformed json is rejected`() {
        assertThrows(JsonSyntaxException::class.java) { BreadcrumbJson.decode("{\"id\":\"x\"}") }
        assertThrows(JsonSyntaxException::class.java) { BreadcrumbJson.decode("[{") }
    }

    @Test
    fun `entries missing required fields or with unknown category are dropped`() {
        val json = """
            [
              {"id":"ok","text":"keep","category":"DECISION","filePath":"a.kt","lineNumber":3},
              {"id":"no-text","category":"DECISION","filePath":"a.kt","lineNumber":1},
              {"id":"bad-cat","text":"x","category":"NOPE","filePath":"a.kt","lineNumber":1},
              {"id":"no-path","text":"x","category":"PATTERN","lineNumber":1},
              null
            ]
        """.trimIndent()
        val decoded = BreadcrumbJson.decode(json)
        assertEquals(listOf("ok"), decoded.map { it.id })
    }

    @Test
    fun `optional fields get safe defaults`() {
        val decoded = BreadcrumbJson.decode(
            """[{"text":"t","category":"CONTEXT","filePath":"a.kt","lineNumber":0,"linkedBreadcrumbs":["l",null]}]"""
        ).single()
        assertFalse(decoded.id.isBlank())
        assertEquals("unknown", decoded.author)
        assertEquals(1, decoded.lineNumber)
        assertEquals(listOf("l"), decoded.linkedBreadcrumbs)
        // search must not NPE on the defaulted author
        assertEquals(1, BreadcrumbQueries.search(listOf(decoded), "unknown").size)
    }
}
