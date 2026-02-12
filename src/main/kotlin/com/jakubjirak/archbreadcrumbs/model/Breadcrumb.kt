package com.jakubjirak.archbreadcrumbs.model

import java.util.UUID

data class Breadcrumb(
    val id: String = UUID.randomUUID().toString(),
    val text: String,
    val category: BreadcrumbCategory,
    val author: String = System.getProperty("user.name", "unknown"),
    val timestamp: Long = System.currentTimeMillis(),
    val filePath: String,
    val lineNumber: Int,
    val linkedBreadcrumbs: List<String> = emptyList(),
)
