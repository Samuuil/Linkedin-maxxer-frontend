package com.linkedinmaxxer.app.domain.repository

import com.linkedinmaxxer.app.domain.model.response.CommentSuggestionResponse
import com.linkedinmaxxer.app.domain.model.response.SubscriptionResponse

interface SubscriptionRepository {
    suspend fun getSubscriptions(): Result<List<SubscriptionResponse>>
    suspend fun subscribe(linkedinUrl: String): Result<SubscriptionResponse>
    suspend fun unsubscribe(subscriptionId: String): Result<Unit>
    suspend fun toggleAutoComment(subscriptionId: String, autoComment: Boolean): Result<Unit>
    suspend fun getSuggestions(onlyPending: Boolean): Result<List<CommentSuggestionResponse>>
    suspend fun respondSuggestion(suggestionId: String, approve: Boolean): Result<CommentSuggestionResponse>
}
