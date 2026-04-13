package com.linkedinmaxxer.app.domain.usecase.subscription

import com.linkedinmaxxer.app.domain.repository.SubscriptionRepository

class ToggleAutoCommentUseCase(
    private val repository: Lazy<SubscriptionRepository>,
) {
    suspend operator fun invoke(subscriptionId: String, autoComment: Boolean) =
        repository.value.toggleAutoComment(subscriptionId, autoComment)
}
