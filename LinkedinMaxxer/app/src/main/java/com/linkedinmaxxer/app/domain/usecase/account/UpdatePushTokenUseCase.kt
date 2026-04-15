package com.linkedinmaxxer.app.domain.usecase.account

import com.linkedinmaxxer.app.domain.repository.AccountRepository

class UpdatePushTokenUseCase(
    private val repository: Lazy<AccountRepository>,
) {
    suspend operator fun invoke(pushToken: String) = repository.value.updatePushToken(pushToken)
}
