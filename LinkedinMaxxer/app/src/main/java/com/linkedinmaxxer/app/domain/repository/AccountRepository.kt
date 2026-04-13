package com.linkedinmaxxer.app.domain.repository

import com.linkedinmaxxer.app.domain.model.response.UserProfile

interface AccountRepository {
    suspend fun profile(): Result<UserProfile>
    suspend fun logout(): Result<Map<String, String>>
}
