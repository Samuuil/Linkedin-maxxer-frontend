package com.linkedinmaxxer.app.domain.usecase.posts

import com.linkedinmaxxer.app.domain.repository.PostsRepository

class GetPostsUseCase(
    private val repository: Lazy<PostsRepository>,
) {
    suspend operator fun invoke(status: String?) = repository.value.getPosts(status)
}
