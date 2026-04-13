package com.linkedinmaxxer.app.domain.model.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PaginatedPostsResponse(
    val data: List<PostResponse> = emptyList(),
    val meta: PaginationMeta? = null,
    val links: PaginationLinks? = null,
)

@Serializable
data class PaginationMeta(
    val totalItems: Int = 0,
    val itemCount: Int = 0,
    val itemsPerPage: Int = 0,
    val totalPages: Int = 0,
    val currentPage: Int = 1,
)

@Serializable
data class PaginationLinks(
    val first: String? = null,
    val previous: String? = null,
    val current: String? = null,
    val next: String? = null,
    val last: String? = null,
)

@Serializable
data class PostResponse(
    val id: String,
    val userId: String,
    val text: String,
    val status: String,
    val error: String? = null,
    val linkedInPostUrn: String? = null,
    val createdAt: String,
    val updatedAt: String,
)

@Serializable
data class EnhancedDescriptionResponse(
    @SerialName("description")
    val description: String,
)
