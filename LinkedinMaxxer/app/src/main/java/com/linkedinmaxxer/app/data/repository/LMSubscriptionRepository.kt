package com.linkedinmaxxer.app.data.repository

import com.linkedinmaxxer.app.data.datasource.SubscriptionDataSource
import com.linkedinmaxxer.app.data.datasource.resultBody
import com.linkedinmaxxer.app.domain.model.response.CommentSuggestionResponse
import com.linkedinmaxxer.app.domain.model.response.SubscriptionResponse
import com.linkedinmaxxer.app.domain.repository.SubscriptionRepository

class LMSubscriptionRepository(
    private val dataSource: SubscriptionDataSource,
) : SubscriptionRepository {
    override suspend fun getSubscriptions(): Result<List<SubscriptionResponse>> =
        resultBody(dataSource.getSubscriptions())

    override suspend fun subscribe(linkedinUrl: String): Result<SubscriptionResponse> =
        resultBody(dataSource.subscribe(linkedinUrl))

    override suspend fun unsubscribe(subscriptionId: String): Result<Unit> =
        resultBody(dataSource.unsubscribe(subscriptionId))

    override suspend fun toggleAutoComment(subscriptionId: String, autoComment: Boolean): Result<Unit> =
        resultBody(dataSource.toggleAutoComment(subscriptionId, autoComment))

    override suspend fun getSuggestions(onlyPending: Boolean): Result<List<CommentSuggestionResponse>> =
        resultBody(dataSource.getSuggestions(onlyPending))

    override suspend fun respondSuggestion(
        suggestionId: String,
        approve: Boolean,
    ): Result<CommentSuggestionResponse> = resultBody(dataSource.respondSuggestion(suggestionId, approve))
}
