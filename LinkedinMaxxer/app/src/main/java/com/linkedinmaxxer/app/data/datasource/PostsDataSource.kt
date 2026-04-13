package com.linkedinmaxxer.app.data.datasource

import com.linkedinmaxxer.app.domain.model.response.EnhancedDescriptionResponse
import com.linkedinmaxxer.app.domain.model.response.PaginatedPostsResponse
import com.linkedinmaxxer.app.domain.model.response.PostResponse

interface PostsDataSource {
    suspend fun getPosts(status: String?): Result<PaginatedPostsResponse>
    suspend fun createPost(text: String): Result<PostResponse>
    suspend fun enhanceDescription(description: String): Result<EnhancedDescriptionResponse>
}
