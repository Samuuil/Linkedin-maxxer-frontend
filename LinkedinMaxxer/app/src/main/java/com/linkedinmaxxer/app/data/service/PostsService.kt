package com.linkedinmaxxer.app.data.service

import com.linkedinmaxxer.app.data.constants.Posts
import com.linkedinmaxxer.app.domain.model.request.CreatePostModel
import com.linkedinmaxxer.app.domain.model.request.EnhanceDescriptionModel
import com.linkedinmaxxer.app.domain.model.response.EnhancedDescriptionResponse
import com.linkedinmaxxer.app.domain.model.response.PaginatedPostsResponse
import com.linkedinmaxxer.app.domain.model.response.PostResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PostsService {
    @GET(Posts.ROOT)
    suspend fun getPosts(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20,
        @Query("filter.status") statusFilter: String? = null,
    ): Response<PaginatedPostsResponse>

    @POST(Posts.ROOT)
    suspend fun createPost(@Body body: CreatePostModel): Response<PostResponse>

    @POST(Posts.ENHANCE)
    suspend fun enhanceDescription(@Body body: EnhanceDescriptionModel): Response<EnhancedDescriptionResponse>
}
