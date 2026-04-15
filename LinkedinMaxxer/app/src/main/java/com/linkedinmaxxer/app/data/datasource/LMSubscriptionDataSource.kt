package com.linkedinmaxxer.app.data.datasource

import com.linkedinmaxxer.app.domain.model.request.RespondSuggestionModel
import com.linkedinmaxxer.app.domain.model.request.SubscribeModel
import com.linkedinmaxxer.app.domain.model.request.ToggleAutoCommentModel
import com.linkedinmaxxer.app.domain.model.response.CommentSuggestionResponse
import com.linkedinmaxxer.app.domain.model.response.SubscriptionResponse
import com.linkedinmaxxer.app.data.service.SubscriptionService

class LMSubscriptionDataSource(
    private val subscriptionService: SubscriptionService,
) : SubscriptionDataSource {
    override suspend fun getSubscriptions(): Result<List<SubscriptionResponse>> =
        safeRequest { subscriptionService.getSubscriptions() }

    override suspend fun subscribe(linkedinUrl: String): Result<SubscriptionResponse> =
        safeRequest { subscriptionService.subscribe(SubscribeModel(username = linkedinUrl)) }

    override suspend fun unsubscribe(subscriptionId: String): Result<Unit> =
        safeRequest { subscriptionService.unsubscribe(subscriptionId) }

    override suspend fun toggleAutoComment(subscriptionId: String, autoComment: Boolean): Result<Unit> =
        safeRequest {
            subscriptionService.toggleAutoComment(
                ToggleAutoCommentModel(subscriptionId = subscriptionId, autoComment = autoComment),
            )
        }

    override suspend fun getSuggestions(onlyPending: Boolean): Result<List<CommentSuggestionResponse>> =
        safeRequest {
            if (onlyPending) subscriptionService.getPendingSuggestions() else subscriptionService.getSuggestions()
        }

    override suspend fun respondSuggestion(
        suggestionId: String,
        approve: Boolean,
    ): Result<CommentSuggestionResponse> =
        safeRequest {
            subscriptionService.respondSuggestion(
                RespondSuggestionModel(
                    suggestionId = suggestionId,
                    approve = approve,
                ),
            )
        }
}
