package com.linkedinmaxxer.app.domain.repository

import com.linkedinmaxxer.app.domain.model.request.LoginModel
import com.linkedinmaxxer.app.domain.model.request.RegisterModel
import com.linkedinmaxxer.app.domain.model.response.AuthTokens

interface AuthRepository {
    suspend fun login(body: LoginModel): Result<AuthTokens>
    suspend fun register(body: RegisterModel): Result<AuthTokens>
    suspend fun refresh(): Result<AuthTokens>
}
