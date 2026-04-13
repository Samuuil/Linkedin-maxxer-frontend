package com.linkedinmaxxer.app.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class LoginModel(
    val email: String,
    val password: String,
)
