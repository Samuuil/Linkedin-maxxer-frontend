package com.linkedinmaxxer.app.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SetLinkedinCredentialsModel(
    val linkedinEmail: String,
    val linkedinPassword: String,
)
