package com.linkedinmaxxer.app.domain.usecase.account

import com.linkedinmaxxer.app.domain.repository.AccountRepository

class SetupLinkedinUseCase(
    private val repository: Lazy<AccountRepository>,
) {
    suspend fun setOfficialToken(token: String) = repository.value.setOfficialToken(token)

    suspend fun setUnofficialToken(token: String) = repository.value.setUnofficialToken(token)

    suspend fun setLinkedinCredentials(email: String, password: String) =
        repository.value.setLinkedinCredentials(email, password)

    suspend fun updatePushToken(pushToken: String) = repository.value.updatePushToken(pushToken)
}
