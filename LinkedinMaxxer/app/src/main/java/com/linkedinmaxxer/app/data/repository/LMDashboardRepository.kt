package com.linkedinmaxxer.app.data.repository

import com.linkedinmaxxer.app.data.datasource.DashboardDataSource
import com.linkedinmaxxer.app.data.datasource.resultBody
import com.linkedinmaxxer.app.domain.model.response.DashboardSummary
import com.linkedinmaxxer.app.domain.repository.DashboardRepository

class LMDashboardRepository(
    private val dataSource: DashboardDataSource,
) : DashboardRepository {
    override suspend fun summary(): Result<DashboardSummary> = resultBody(dataSource.summary())
}
