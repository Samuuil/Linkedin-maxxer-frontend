package com.linkedinmaxxer.app.domain.usecase.subscription

import com.linkedinmaxxer.app.domain.repository.SubscriptionRepository

class UnsubscribeUseCase(
    private val repository: Lazy<SubscriptionRepository>,
) {
    suspend operator fun invoke(subscriptionId: String) = repository.value.unsubscribe(subscriptionId)
}
