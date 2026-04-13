package com.linkedinmaxxer.app.domain.model.response

import kotlinx.serialization.Serializable

@Serializable
data class DashboardActivityItem(
    val id: String,
    val type: String,
    val title: String,
    val subtitle: String,
    val status: String,
    val createdAt: String,
)
