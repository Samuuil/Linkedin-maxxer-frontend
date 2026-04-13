package com.linkedinmaxxer.app.domain.usecase.dashboard

import com.linkedinmaxxer.app.domain.repository.DashboardRepository

class GetDashboardSummaryUseCase(
    private val repository: Lazy<DashboardRepository>,
) {
    suspend operator fun invoke() = repository.value.summary()
}
