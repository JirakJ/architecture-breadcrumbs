package com.jakubjirak.archbreadcrumbs.store

import com.jakubjirak.archbreadcrumbs.model.Breadcrumb
import com.jakubjirak.archbreadcrumbs.model.BreadcrumbCategory

/** Pure query helpers shared by [BreadcrumbStore] and the tool window. */
internal object BreadcrumbQueries {

    /** Case-insensitive match on text, author, file path or category name; a blank query matches everything. */
    fun search(breadcrumbs: List<Breadcrumb>, query: String): List<Breadcrumb> {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return breadcrumbs
        return breadcrumbs.filter {
            it.text.lowercase().contains(q) ||
                it.author.lowercase().contains(q) ||
                it.filePath.lowercase().contains(q) ||
                it.category.displayName.lowercase().contains(q)
        }
    }

    /** Applies the optional [category] filter and then the free-text [query]. */
    fun filter(breadcrumbs: List<Breadcrumb>, category: BreadcrumbCategory?, query: String): List<Breadcrumb> {
        val byCategory = if (category == null) breadcrumbs else breadcrumbs.filter { it.category == category }
        return search(byCategory, query)
    }

    /** Parses the comma-separated "linked breadcrumbs" input into distinct, non-blank ids. */
    fun parseLinkedIds(text: String?): List<String> =
        text.orEmpty().split(",").map { it.trim() }.filter { it.isNotEmpty() }.distinct()

    /** Converts an absolute file path to the project-relative form used in the store. */
    fun relativePath(basePath: String, filePath: String): String {
        val base = basePath.trimEnd('/')
        return if (filePath == base || filePath.startsWith("$base/")) filePath.removePrefix(base).removePrefix("/") else filePath
    }
}
