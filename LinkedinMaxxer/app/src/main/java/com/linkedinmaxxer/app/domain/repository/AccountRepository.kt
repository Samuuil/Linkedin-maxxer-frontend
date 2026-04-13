package com.linkedinmaxxer.app.domain.repository

import com.linkedinmaxxer.app.domain.model.response.UserProfile

interface AccountRepository {
    suspend fun profile(): Result<UserProfile>
    suspend fun logout(): Result<Map<String, String>>
    suspend fun setOfficialToken(token: String): Result<Unit>
    suspend fun setUnofficialToken(token: String): Result<Unit>
    suspend fun setLinkedinCredentials(email: String, password: String): Result<Unit>
    suspend fun updatePushToken(pushToken: String): Result<Unit>
}
