package com.linkedinmaxxer.app.data.datasource

import com.linkedinmaxxer.app.data.service.PostsService
import com.linkedinmaxxer.app.domain.model.request.CreatePostModel
import com.linkedinmaxxer.app.domain.model.request.EnhanceDescriptionModel
import com.linkedinmaxxer.app.domain.model.response.EnhancedDescriptionResponse
import com.linkedinmaxxer.app.domain.model.response.PaginatedPostsResponse
import com.linkedinmaxxer.app.domain.model.response.PostResponse

class LMPostsDataSource(
    private val postsService: PostsService,
) : PostsDataSource {
    override suspend fun getPosts(status: String?): Result<PaginatedPostsResponse> {
        val filter = status?.let { "\$eq:$it" }
        return safeRequest { postsService.getPosts(statusFilter = filter) }
    }

    override suspend fun createPost(text: String): Result<PostResponse> =
        safeRequest { postsService.createPost(CreatePostModel(text = text)) }

    override suspend fun enhanceDescription(description: String): Result<EnhancedDescriptionResponse> =
        safeRequest { postsService.enhanceDescription(EnhanceDescriptionModel(description = description)) }
}
