package com.linkedinmaxxer.app.data.datasource

import com.linkedinmaxxer.app.domain.model.response.UserProfile

interface AccountDataSource {
    suspend fun profile(): Result<UserProfile>
    suspend fun logout(): Result<Map<String, String>>
}
