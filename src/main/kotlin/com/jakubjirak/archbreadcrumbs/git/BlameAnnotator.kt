package com.jakubjirak.archbreadcrumbs.git

import com.intellij.openapi.diagnostic.Logger
import com.jakubjirak.archbreadcrumbs.model.Breadcrumb
import java.time.Instant
import java.time.temporal.ChronoUnit

/**
 * Enriches breadcrumbs with git blame information.
 * Detects stale breadcrumbs where the surrounding code changed but the breadcrumb was not updated.
 */
class BlameAnnotator(
    private val staleThresholdDays: Long = DEFAULT_STALE_THRESHOLD_DAYS,
) {

    fun createBlameInfo(author: String, date: Instant, commitHash: String): BlameInfo {
        return BlameInfo(
            author = author,
            date = date,
            commitHash = commitHash,
        )
    }

    fun isStale(breadcrumbDate: Instant, lastCodeChangeDate: Instant?): Boolean {
        if (lastCodeChangeDate == null) return false
        val breadcrumbAge = ChronoUnit.DAYS.between(breadcrumbDate, lastCodeChangeDate)
        return breadcrumbAge > staleThresholdDays
    }

    fun staleDays(breadcrumbDate: Instant, lastCodeChangeDate: Instant?): Long? {
        if (lastCodeChangeDate == null) return null
        val days = ChronoUnit.DAYS.between(breadcrumbDate, lastCodeChangeDate)
        return if (days > staleThresholdDays) days else null
    }

    @Suppress("UNUSED_PARAMETER")
    fun annotate(
        breadcrumb: Breadcrumb,
        blameInfo: BlameInfo,
        lastCodeChangeDate: Instant?,
    ): BlameAnnotatedBreadcrumb {
        val stale = isStale(blameInfo.date, lastCodeChangeDate)
        val days = staleDays(blameInfo.date, lastCodeChangeDate)
        return BlameAnnotatedBreadcrumb(
            breadcrumb = breadcrumb,
            blameInfo = blameInfo,
            lastCodeChangeDate = lastCodeChangeDate,
            isStale = stale,
            staleDays = days,
        )
    }

    companion object {
        @Suppress("unused")
        private val LOG = Logger.getInstance(BlameAnnotator::class.java)

        const val DEFAULT_STALE_THRESHOLD_DAYS = 30L
    }
}

data class BlameInfo(
    val author: String,
    val date: Instant,
    val commitHash: String,
)

data class BlameAnnotatedBreadcrumb(
    val breadcrumb: Breadcrumb,
    val blameInfo: BlameInfo,
    val lastCodeChangeDate: Instant?,
    val isStale: Boolean,
    val staleDays: Long?,
)
