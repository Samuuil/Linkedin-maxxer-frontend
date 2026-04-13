package com.linkedinmaxxer.app.domain.repository

import com.linkedinmaxxer.app.domain.model.response.EnhancedDescriptionResponse
import com.linkedinmaxxer.app.domain.model.response.PaginatedPostsResponse
import com.linkedinmaxxer.app.domain.model.response.PostResponse

interface PostsRepository {
    suspend fun getPosts(status: String?): Result<PaginatedPostsResponse>
    suspend fun createPost(text: String): Result<PostResponse>
    suspend fun enhanceDescription(description: String): Result<EnhancedDescriptionResponse>
}
