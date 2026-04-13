package com.linkedinmaxxer.app.data.service

import com.linkedinmaxxer.app.data.constants.Dashboard
import com.linkedinmaxxer.app.domain.model.response.DashboardSummary
import retrofit2.Response
import retrofit2.http.GET

interface DashboardService {
    @GET(Dashboard.SUMMARY)
    suspend fun summary(): Response<DashboardSummary>
}
