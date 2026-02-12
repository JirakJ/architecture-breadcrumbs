package com.jakubjirak.archbreadcrumbs.model

enum class BreadcrumbCategory(val displayName: String, val icon: String) {
    DECISION("Decision", "\uD83D\uDCCB"),
    PATTERN("Pattern", "\uD83D\uDD27"),
    CONSTRAINT("Constraint", "\uD83D\uDD12"),
    TODO_ARCH("TODO", "\uD83D\uDCDD"),
    WARNING("Warning", "\u26A0\uFE0F"),
    CONTEXT("Context", "\uD83D\uDCA1"),
}
