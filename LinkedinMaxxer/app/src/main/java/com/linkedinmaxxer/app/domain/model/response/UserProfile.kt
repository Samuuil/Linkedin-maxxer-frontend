package com.linkedinmaxxer.app.domain.model.response

import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val id: String,
    val email: String,
    val linkedinUsername: String? = null,
    val linkedinSub: String? = null,
    val pushToken: String? = null,
    val createdAt: String,
    val updatedAt: String,
)
