package com.jakubjirak.archbreadcrumbs.licensing

import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.components.Service

/**
 * Central gate for freemium feature access.
 * Free tier: up to 20 breadcrumbs, basic categories.
 * Paid tier: unlimited breadcrumbs, cross-linking, export.
 */
@Service(Service.Level.APP)
class FreemiumGate {

    enum class LicenseStatus {
        FREE, TRIAL, PAID, EXPIRED
    }

    @Suppress("UNUSED_PARAMETER")
    fun isPaidFeatureAvailable(featureId: String): Boolean {
        val status = getLicenseStatus()
        return status == LicenseStatus.PAID || status == LicenseStatus.TRIAL
    }

    fun getLicenseStatus(): LicenseStatus {
        // TODO: Integrate with JetBrains Marketplace Licensing API
        return LicenseStatus.FREE
    }

    fun getMaxFreeBreadcrumbs(): Int {
        return when (getLicenseStatus()) {
            LicenseStatus.FREE -> MAX_FREE_BREADCRUMBS
            LicenseStatus.EXPIRED -> MAX_FREE_BREADCRUMBS
            LicenseStatus.TRIAL, LicenseStatus.PAID -> Int.MAX_VALUE
        }
    }

    fun canAddBreadcrumb(currentCount: Int): Boolean {
        return currentCount < getMaxFreeBreadcrumbs()
    }

    companion object {
        const val MAX_FREE_BREADCRUMBS = 20

        const val FEATURE_UNLIMITED = "unlimited_breadcrumbs"
        const val FEATURE_CROSS_LINKING = "cross_linking"
        const val FEATURE_EXPORT = "export"

        fun getInstance(): FreemiumGate =
            ApplicationManager.getApplication().getService(FreemiumGate::class.java)
    }
}
