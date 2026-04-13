package com.linkedinmaxxer.app.domain.usecase.subscription

import com.linkedinmaxxer.app.domain.repository.SubscriptionRepository

class SubscribeUseCase(
    private val repository: Lazy<SubscriptionRepository>,
) {
    suspend operator fun invoke(linkedinUrl: String) = repository.value.subscribe(linkedinUrl)
}
