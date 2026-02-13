package com.jakubjirak.archbreadcrumbs.git

import com.intellij.openapi.diagnostic.Logger
import com.jakubjirak.archbreadcrumbs.model.Breadcrumb

/**
 * Compares breadcrumbs across branches to identify architectural impact of branch changes.
 * Shows which architectural decisions differ between branches.
 */
class BranchImpactAnalyzer {

    fun analyze(
        sourceBranch: String,
        targetBranch: String,
        sourceBreadcrumbs: List<Breadcrumb>,
        targetBreadcrumbs: List<Breadcrumb>,
    ): BranchImpact {
        val sourceById = sourceBreadcrumbs.associateBy { it.id }
        val targetById = targetBreadcrumbs.associateBy { it.id }

        val added = sourceBreadcrumbs.filter { it.id !in targetById }
        val removed = targetBreadcrumbs.filter { it.id !in sourceById }
        val modified = sourceBreadcrumbs.filter { breadcrumb ->
            val targetVersion = targetById[breadcrumb.id]
            targetVersion != null && targetVersion != breadcrumb
        }

        return BranchImpact(
            branchA = sourceBranch,
            branchB = targetBranch,
            addedBreadcrumbs = added,
            removedBreadcrumbs = removed,
            modifiedBreadcrumbs = modified,
        )
    }

    fun hasImpact(impact: BranchImpact): Boolean {
        return impact.addedBreadcrumbs.isNotEmpty() ||
            impact.removedBreadcrumbs.isNotEmpty() ||
            impact.modifiedBreadcrumbs.isNotEmpty()
    }

    fun impactSummary(impact: BranchImpact): String {
        val parts = mutableListOf<String>()
        if (impact.addedBreadcrumbs.isNotEmpty()) {
            parts.add("${impact.addedBreadcrumbs.size} added")
        }
        if (impact.removedBreadcrumbs.isNotEmpty()) {
            parts.add("${impact.removedBreadcrumbs.size} removed")
        }
        if (impact.modifiedBreadcrumbs.isNotEmpty()) {
            parts.add("${impact.modifiedBreadcrumbs.size} modified")
        }
        if (parts.isEmpty()) return "No architectural changes"
        return "Architectural changes: ${parts.joinToString(", ")}"
    }

    companion object {
        @Suppress("unused")
        private val LOG = Logger.getInstance(BranchImpactAnalyzer::class.java)
    }
}

data class BranchImpact(
    val branchA: String,
    val branchB: String,
    val addedBreadcrumbs: List<Breadcrumb>,
    val removedBreadcrumbs: List<Breadcrumb>,
    val modifiedBreadcrumbs: List<Breadcrumb>,
)
