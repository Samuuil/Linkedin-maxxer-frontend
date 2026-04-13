package com.linkedinmaxxer.app.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class RegisterModel(
    val email: String,
    val password: String,
)
