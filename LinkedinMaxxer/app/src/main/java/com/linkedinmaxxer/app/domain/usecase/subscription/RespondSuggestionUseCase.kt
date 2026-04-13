package com.linkedinmaxxer.app.domain.usecase.subscription

import com.linkedinmaxxer.app.domain.repository.SubscriptionRepository

class RespondSuggestionUseCase(
    private val repository: Lazy<SubscriptionRepository>,
) {
    suspend operator fun invoke(suggestionId: String, approve: Boolean) =
        repository.value.respondSuggestion(suggestionId, approve)
}
