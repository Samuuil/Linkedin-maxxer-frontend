package com.linkedinmaxxer.app.domain.repository

import com.linkedinmaxxer.app.domain.model.response.DashboardSummary

interface DashboardRepository {
    suspend fun summary(): Result<DashboardSummary>
}
