package com.linkedinmaxxer.app.domain.model.request

import kotlinx.serialization.Serializable

@Serializable
data class SetOfficialTokenModel(
    val oficialToken: String,
)
