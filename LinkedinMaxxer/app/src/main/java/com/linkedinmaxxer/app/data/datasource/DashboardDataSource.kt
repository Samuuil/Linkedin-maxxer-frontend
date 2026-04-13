package com.linkedinmaxxer.app.data.datasource

import com.linkedinmaxxer.app.domain.model.response.DashboardSummary

interface DashboardDataSource {
    suspend fun summary(): Result<DashboardSummary>
}
