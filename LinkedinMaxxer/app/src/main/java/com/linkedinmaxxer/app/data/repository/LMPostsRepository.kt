package com.linkedinmaxxer.app.data.repository

import com.linkedinmaxxer.app.data.datasource.PostsDataSource
import com.linkedinmaxxer.app.data.datasource.resultBody
import com.linkedinmaxxer.app.domain.model.response.EnhancedDescriptionResponse
import com.linkedinmaxxer.app.domain.model.response.PaginatedPostsResponse
import com.linkedinmaxxer.app.domain.model.response.PostResponse
import com.linkedinmaxxer.app.domain.repository.PostsRepository

class LMPostsRepository(
    private val dataSource: PostsDataSource,
) : PostsRepository {
    override suspend fun getPosts(status: String?): Result<PaginatedPostsResponse> =
        resultBody(dataSource.getPosts(status))

    override suspend fun createPost(text: String): Result<PostResponse> =
        resultBody(dataSource.createPost(text))

    override suspend fun enhanceDescription(description: String): Result<EnhancedDescriptionResponse> =
        resultBody(dataSource.enhanceDescription(description))
}
