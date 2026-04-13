package com.linkedinmaxxer.app.data.datasource

import com.linkedinmaxxer.app.data.service.DashboardService
import com.linkedinmaxxer.app.domain.model.response.DashboardSummary

class LMDashboardDataSource(
    private val dashboardService: DashboardService,
) : DashboardDataSource {
    override suspend fun summary(): Result<DashboardSummary> = requestBody(dashboardService.summary())
}
