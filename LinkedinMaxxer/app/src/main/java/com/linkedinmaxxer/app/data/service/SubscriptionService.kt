package com.linkedinmaxxer.app.data.service

import com.linkedinmaxxer.app.data.constants.Subscription
import com.linkedinmaxxer.app.domain.model.request.RespondSuggestionModel
import com.linkedinmaxxer.app.domain.model.request.SubscribeModel
import com.linkedinmaxxer.app.domain.model.request.ToggleAutoCommentModel
import com.linkedinmaxxer.app.domain.model.response.CommentSuggestionResponse
import com.linkedinmaxxer.app.domain.model.response.SubscriptionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SubscriptionService {
    @GET(Subscription.ROOT)
    suspend fun getSubscriptions(): Response<List<SubscriptionResponse>>

    @POST(Subscription.ROOT)
    suspend fun subscribe(@Body body: SubscribeModel): Response<SubscriptionResponse>

    @DELETE("${Subscription.ROOT}/{id}")
    suspend fun unsubscribe(@Path("id") subscriptionId: String): Response<Unit>

    @POST(Subscription.AUTO_COMMENT)
    suspend fun toggleAutoComment(@Body body: ToggleAutoCommentModel): Response<Unit>

    @GET(Subscription.SUGGESTIONS)
    suspend fun getSuggestions(): Response<List<CommentSuggestionResponse>>

    @GET(Subscription.PENDING_SUGGESTIONS)
    suspend fun getPendingSuggestions(): Response<List<CommentSuggestionResponse>>

    @POST(Subscription.RESPOND)
    suspend fun respondSuggestion(@Body body: RespondSuggestionModel): Response<CommentSuggestionResponse>
}
