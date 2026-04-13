package com.linkedinmaxxer.app.data.datasource

import com.linkedinmaxxer.app.data.service.AuthService
import com.linkedinmaxxer.app.domain.model.response.UserProfile

class LMAccountDataSource(
    private val authService: AuthService,
) : AccountDataSource {
    override suspend fun profile(): Result<UserProfile> = requestBody(authService.profile())

    override suspend fun logout(): Result<Map<String, String>> = requestBody(authService.logout())
}
