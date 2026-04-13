package com.linkedinmaxxer.app.domain.usecase.subscription

import com.linkedinmaxxer.app.domain.repository.SubscriptionRepository

class GetSuggestionsUseCase(
    private val repository: Lazy<SubscriptionRepository>,
) {
    suspend operator fun invoke(onlyPending: Boolean) = repository.value.getSuggestions(onlyPending)
}
