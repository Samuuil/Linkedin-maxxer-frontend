package com.linkedinmaxxer.app.data.repository

import com.linkedinmaxxer.app.data.datasource.AccountDataSource
import com.linkedinmaxxer.app.data.datasource.resultBody
import com.linkedinmaxxer.app.domain.model.response.UserProfile
import com.linkedinmaxxer.app.domain.repository.AccountRepository

class LMAccountRepository(
    private val dataSource: AccountDataSource,
) : AccountRepository {
    override suspend fun profile(): Result<UserProfile> = resultBody(dataSource.profile())

    override suspend fun logout(): Result<Map<String, String>> = resultBody(dataSource.logout())

    override suspend fun setOfficialToken(token: String): Result<Unit> = resultBody(dataSource.setOfficialToken(token))

    override suspend fun setUnofficialToken(token: String): Result<Unit> = resultBody(dataSource.setUnofficialToken(token))

    override suspend fun setLinkedinCredentials(email: String, password: String): Result<Unit> =
        resultBody(dataSource.setLinkedinCredentials(email, password))

    override suspend fun updatePushToken(pushToken: String): Result<Unit> =
        resultBody(dataSource.updatePushToken(pushToken))
}
