package com.linkedinmaxxer.app.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeModel(
    val linkedinUrl: String,
)

@Serializable
data class ToggleAutoCommentModel(
    val subscriptionId: String,
    val autoComment: Boolean,
)

@Serializable
data class RespondSuggestionModel(
    val suggestionId: String,
    val approve: Boolean,
)
