package com.linkedinmaxxer.app.domain.usecase.subscription

import com.linkedinmaxxer.app.domain.repository.SubscriptionRepository

class GetSubscriptionsUseCase(
    private val repository: Lazy<SubscriptionRepository>,
) {
    suspend operator fun invoke() = repository.value.getSubscriptions()
}
