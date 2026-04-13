package com.linkedinmaxxer.app.domain.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    @SerialName("message")
    val message: String? = null,
    @SerialName("error")
    val error: String? = null,
    @SerialName("statusCode")
    val statusCode: Int? = null,
)
