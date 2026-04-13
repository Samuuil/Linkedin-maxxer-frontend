package com.linkedinmaxxer.app.data.repository

import com.linkedinmaxxer.app.data.datasource.AuthDataSource
import com.linkedinmaxxer.app.data.datasource.resultBody
import com.linkedinmaxxer.app.domain.model.request.LoginModel
import com.linkedinmaxxer.app.domain.model.request.RegisterModel
import com.linkedinmaxxer.app.domain.model.response.AuthTokens
import com.linkedinmaxxer.app.domain.repository.AuthRepository

class LMAuthRepository(
    private val authDataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun login(body: LoginModel): Result<AuthTokens> = resultBody(authDataSource.login(body))

    override suspend fun register(body: RegisterModel): Result<AuthTokens> = resultBody(authDataSource.register(body))

    override suspend fun refresh(): Result<AuthTokens> = resultBody(authDataSource.refresh())
}
