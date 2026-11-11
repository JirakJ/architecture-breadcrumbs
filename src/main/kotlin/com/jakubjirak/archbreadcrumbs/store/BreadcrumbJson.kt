package com.jakubjirak.archbreadcrumbs.store

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.jakubjirak.archbreadcrumbs.model.Breadcrumb

/**
 * Reads and writes the `.arch-breadcrumbs.json` file format.
 *
 * Gson bypasses Kotlin constructors, so a hand-edited or partially written file can produce
 * breadcrumbs whose non-null fields are actually `null`. [decode] drops entries that miss a
 * required field (text, category, file path) and fills optional ones with safe defaults.
 */
internal object BreadcrumbJson {

    private val gson = GsonBuilder().setPrettyPrinting().create()
    private val listType = object : TypeToken<List<Breadcrumb?>>() {}.type

    fun encode(breadcrumbs: List<Breadcrumb>): String = gson.toJson(breadcrumbs)

    /**
     * @throws JsonSyntaxException when the text is not valid JSON or not a JSON array.
     */
    fun decode(json: String): List<Breadcrumb> {
        if (json.isBlank()) return emptyList()
        if (!JsonParser.parseString(json).isJsonArray) throw JsonSyntaxException("Expected a JSON array of breadcrumbs")
        val raw: List<Breadcrumb?> = gson.fromJson(json, listType) ?: return emptyList()
        return raw.mapNotNull { sanitize(it) }
    }

    @Suppress("SENSELESS_COMPARISON", "USELESS_ELVIS")
    private fun sanitize(b: Breadcrumb?): Breadcrumb? {
        if (b == null) return null
        val requiredFields: List<Any?> = listOf(b.text, b.category, b.filePath)
        if (requiredFields.any { it == null }) return null
        return b.copy(
            id = b.id ?: java.util.UUID.randomUUID().toString(),
            author = b.author ?: "unknown",
            linkedBreadcrumbs = (b.linkedBreadcrumbs ?: emptyList()).filterNotNull(),
            lineNumber = b.lineNumber.coerceAtLeast(1),
        )
    }
}
