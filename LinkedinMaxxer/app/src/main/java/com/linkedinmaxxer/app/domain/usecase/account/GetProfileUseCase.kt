package com.linkedinmaxxer.app.domain.usecase.account

import com.linkedinmaxxer.app.domain.repository.AccountRepository

class GetProfileUseCase(
    private val repository: Lazy<AccountRepository>,
) {
    suspend operator fun invoke() = repository.value.profile()
}
