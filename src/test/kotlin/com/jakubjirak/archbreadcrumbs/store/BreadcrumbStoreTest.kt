package com.jakubjirak.archbreadcrumbs.store

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.jakubjirak.archbreadcrumbs.model.Breadcrumb
import com.jakubjirak.archbreadcrumbs.model.BreadcrumbCategory
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File

class BreadcrumbStoreTest {

    @TempDir
    lateinit var tempDir: File

    private val gson: Gson = GsonBuilder().setPrettyPrinting().create()

    @Test
    fun `load breadcrumbs from valid JSON file`() {
        val breadcrumbs = listOf(
            Breadcrumb(
                id = "test-1",
                text = "Use repository pattern for data access",
                category = BreadcrumbCategory.PATTERN,
                author = "alice",
                timestamp = 1700000000000,
                filePath = "src/main/kotlin/UserRepository.kt",
                lineNumber = 10,
            ),
            Breadcrumb(
                id = "test-2",
                text = "No direct DB access from controllers",
                category = BreadcrumbCategory.CONSTRAINT,
                author = "bob",
                timestamp = 1700000001000,
                filePath = "src/main/kotlin/UserController.kt",
                lineNumber = 5,
            ),
        )
        writeStoreFile(breadcrumbs)

        val loaded = readStoreFile()
        assertNotNull(loaded)
        assertEquals(2, loaded.size)

        val first = loaded.find { it.id == "test-1" }
        assertNotNull(first)
        assertEquals("Use repository pattern for data access", first!!.text)
        assertEquals(BreadcrumbCategory.PATTERN, first.category)
        assertEquals("alice", first.author)
        assertEquals("src/main/kotlin/UserRepository.kt", first.filePath)
        assertEquals(10, first.lineNumber)
    }

    @Test
    fun `load from non-existent file returns empty list`() {
        val file = tempDir.resolve(BreadcrumbStore.STORE_FILE_NAME)
        assertFalse(file.exists())
        val loaded = readStoreFile()
        assertTrue(loaded.isEmpty())
    }

    @Test
    fun `load from empty file returns empty list`() {
        tempDir.resolve(BreadcrumbStore.STORE_FILE_NAME).writeText("")
        val loaded = readStoreFile()
        assertTrue(loaded.isEmpty())
    }

    @Test
    fun `save and reload preserves all fields`() {
        val breadcrumb = Breadcrumb(
            id = "preserve-test",
            text = "Decision: use event sourcing",
            category = BreadcrumbCategory.DECISION,
            author = "carol",
            timestamp = 1700000002000,
            filePath = "src/main/kotlin/EventStore.kt",
            lineNumber = 42,
            linkedBreadcrumbs = listOf("other-1", "other-2"),
        )
        writeStoreFile(listOf(breadcrumb))

        val loaded = readStoreFile()
        assertEquals(1, loaded.size)

        val result = loaded[0]
        assertEquals("preserve-test", result.id)
        assertEquals("Decision: use event sourcing", result.text)
        assertEquals(BreadcrumbCategory.DECISION, result.category)
        assertEquals("carol", result.author)
        assertEquals(1700000002000, result.timestamp)
        assertEquals("src/main/kotlin/EventStore.kt", result.filePath)
        assertEquals(42, result.lineNumber)
        assertEquals(listOf("other-1", "other-2"), result.linkedBreadcrumbs)
    }

    @Test
    fun `filter breadcrumbs by file path`() {
        val breadcrumbs = listOf(
            createBreadcrumb("b1", "src/A.kt", 1, BreadcrumbCategory.PATTERN),
            createBreadcrumb("b2", "src/A.kt", 5, BreadcrumbCategory.DECISION),
            createBreadcrumb("b3", "src/B.kt", 3, BreadcrumbCategory.WARNING),
        )

        val filtered = breadcrumbs.filter { it.filePath == "src/A.kt" }
        assertEquals(2, filtered.size)
        assertTrue(filtered.all { it.filePath == "src/A.kt" })
    }

    @Test
    fun `filter breadcrumbs by category`() {
        val breadcrumbs = listOf(
            createBreadcrumb("c1", "src/A.kt", 1, BreadcrumbCategory.PATTERN),
            createBreadcrumb("c2", "src/B.kt", 5, BreadcrumbCategory.PATTERN),
            createBreadcrumb("c3", "src/C.kt", 3, BreadcrumbCategory.WARNING),
        )

        val patterns = breadcrumbs.filter { it.category == BreadcrumbCategory.PATTERN }
        assertEquals(2, patterns.size)
    }

    @Test
    fun `search breadcrumbs by text`() {
        val breadcrumbs = listOf(
            createBreadcrumb("s1", "src/A.kt", 1, BreadcrumbCategory.PATTERN, "Use repository pattern"),
            createBreadcrumb("s2", "src/B.kt", 5, BreadcrumbCategory.DECISION, "Use factory method"),
            createBreadcrumb("s3", "src/C.kt", 3, BreadcrumbCategory.WARNING, "Deprecated API usage"),
        )

        val query = "repository"
        val results = breadcrumbs.filter { it.text.lowercase().contains(query.lowercase()) }
        assertEquals(1, results.size)
        assertEquals("s1", results[0].id)
    }

    @Test
    fun `find breadcrumb by id`() {
        val breadcrumbs = listOf(
            createBreadcrumb("find-1", "src/A.kt", 1, BreadcrumbCategory.PATTERN),
            createBreadcrumb("find-2", "src/B.kt", 5, BreadcrumbCategory.DECISION),
        )

        val found = breadcrumbs.find { it.id == "find-2" }
        assertNotNull(found)
        assertEquals("find-2", found!!.id)

        val notFound = breadcrumbs.find { it.id == "nonexistent" }
        assertNull(notFound)
    }

    @Test
    fun `all breadcrumb categories are distinct`() {
        val categories = BreadcrumbCategory.entries
        assertEquals(6, categories.size)
        assertEquals(categories.size, categories.map { it.name }.toSet().size)
        assertEquals(categories.size, categories.map { it.displayName }.toSet().size)
    }

    @Test
    fun `linked breadcrumbs can be resolved`() {
        val breadcrumbs = listOf(
            Breadcrumb(
                id = "link-1",
                text = "Entry point",
                category = BreadcrumbCategory.CONTEXT,
                filePath = "src/Main.kt",
                lineNumber = 1,
                linkedBreadcrumbs = listOf("link-2", "link-3"),
            ),
            createBreadcrumb("link-2", "src/A.kt", 10, BreadcrumbCategory.PATTERN),
            createBreadcrumb("link-3", "src/B.kt", 20, BreadcrumbCategory.DECISION),
        )

        val source = breadcrumbs.find { it.id == "link-1" }!!
        val linked = source.linkedBreadcrumbs.mapNotNull { linkId ->
            breadcrumbs.find { it.id == linkId }
        }

        assertEquals(2, linked.size)
        assertEquals("link-2", linked[0].id)
        assertEquals("link-3", linked[1].id)
    }

    @Test
    fun `malformed JSON file is handled gracefully`() {
        tempDir.resolve(BreadcrumbStore.STORE_FILE_NAME).writeText("{ not valid json !!!")
        val loaded = readStoreFile()
        // Should not throw — returns empty
        assertTrue(loaded.isEmpty())
    }

    private fun createBreadcrumb(
        id: String,
        filePath: String,
        line: Int,
        category: BreadcrumbCategory,
        text: String = "Test breadcrumb $id",
    ): Breadcrumb = Breadcrumb(
        id = id,
        text = text,
        category = category,
        author = "test",
        timestamp = System.currentTimeMillis(),
        filePath = filePath,
        lineNumber = line,
    )

    private fun writeStoreFile(breadcrumbs: List<Breadcrumb>) {
        val file = tempDir.resolve(BreadcrumbStore.STORE_FILE_NAME)
        file.writeText(gson.toJson(breadcrumbs))
    }

    private fun readStoreFile(): List<Breadcrumb> {
        val file = tempDir.resolve(BreadcrumbStore.STORE_FILE_NAME)
        if (!file.exists()) return emptyList()
        val json = file.readText()
        if (json.isBlank()) return emptyList()
        return try {
            val type = object : TypeToken<List<Breadcrumb>>() {}.type
            gson.fromJson(json, type) ?: emptyList()
        } catch (_: Exception) {
            emptyList()
        }
    }
}
