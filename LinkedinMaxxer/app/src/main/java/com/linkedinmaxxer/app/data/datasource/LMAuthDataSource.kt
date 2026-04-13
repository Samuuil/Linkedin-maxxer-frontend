package com.linkedinmaxxer.app.data.datasource

import com.linkedinmaxxer.app.data.service.AuthService
import com.linkedinmaxxer.app.domain.model.request.LoginModel
import com.linkedinmaxxer.app.domain.model.request.RegisterModel
import com.linkedinmaxxer.app.domain.model.response.AuthTokens

class LMAuthDataSource(
    private val authService: AuthService,
) : AuthDataSource {
    override suspend fun login(body: LoginModel): Result<AuthTokens> = requestBody(authService.login(body))

    override suspend fun register(body: RegisterModel): Result<AuthTokens> = requestBody(authService.register(body))

    override suspend fun refresh(): Result<AuthTokens> = requestBody(authService.refresh())
}
