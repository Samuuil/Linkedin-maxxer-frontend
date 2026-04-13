package com.linkedinmaxxer.app.domain.model.response

import kotlinx.serialization.Serializable

@Serializable
data class SubscriptionResponse(
    val id: String,
    val userId: String,
    val linkedinUsername: String,
    val autoComment: Boolean,
    val createdAt: String,
)

@Serializable
data class CommentSuggestionResponse(
    val id: String,
    val userId: String,
    val linkedinPostId: String,
    val linkedinUsername: String,
    val postDescription: String,
    val suggestedComment: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String,
)
