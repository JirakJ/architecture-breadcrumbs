package com.jakubjirak.archbreadcrumbs.git

import com.intellij.openapi.diagnostic.Logger
import com.jakubjirak.archbreadcrumbs.model.Breadcrumb
import java.time.Instant

/**
 * Extends breadcrumbs with commit linkage for traceability.
 * Each breadcrumb can be associated with the commit where the architectural decision was made.
 */
class CommitLinkedBreadcrumb(
    val breadcrumb: Breadcrumb,
    val commitLink: CommitLink? = null,
    val adrId: String? = null,
) {

    fun hasCommitLink(): Boolean = commitLink != null

    fun hasAdrLink(): Boolean = adrId != null

    companion object {
        @Suppress("unused")
        private val LOG = Logger.getInstance(CommitLinkedBreadcrumb::class.java)

        fun fromBreadcrumb(breadcrumb: Breadcrumb): CommitLinkedBreadcrumb {
            return CommitLinkedBreadcrumb(breadcrumb = breadcrumb)
        }

        fun withCommit(
            breadcrumb: Breadcrumb,
            commitHash: String,
            message: String,
            author: String,
            date: Instant,
        ): CommitLinkedBreadcrumb {
            return CommitLinkedBreadcrumb(
                breadcrumb = breadcrumb,
                commitLink = CommitLink(
                    commitHash = commitHash,
                    message = message,
                    author = author,
                    date = date,
                ),
            )
        }
    }
}

data class CommitLink(
    val commitHash: String,
    val message: String,
    val author: String,
    val date: Instant,
)
