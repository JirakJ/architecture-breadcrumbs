package com.jakubjirak.archbreadcrumbs

import com.intellij.openapi.diagnostic.Logger
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity
import com.jakubjirak.archbreadcrumbs.store.BreadcrumbStore

class ArchBreadcrumbsStartupActivity : ProjectActivity {

    override suspend fun execute(project: Project) {
        try {
            LOG.info("Architecture Breadcrumbs starting up")

            val store = BreadcrumbStore.getInstance(project)
            store.loadBreadcrumbs()

            LOG.info("Architecture Breadcrumbs started successfully")
        } catch (e: Exception) {
            LOG.warn("Failed to start Architecture Breadcrumbs", e)
        }
    }

    companion object {
        private val LOG = Logger.getInstance(ArchBreadcrumbsStartupActivity::class.java)
    }
}
