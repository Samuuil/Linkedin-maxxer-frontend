package com.linkedinmaxxer.app.data.session

import kotlinx.serialization.Serializable

@Serializable
data class AppSettings(
    val token: String = "",
    val refreshToken: String = "",
)
