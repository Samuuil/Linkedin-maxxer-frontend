package com.linkedinmaxxer.app.domain.usecase.posts

import com.linkedinmaxxer.app.domain.repository.PostsRepository

class CreatePostUseCase(
    private val repository: Lazy<PostsRepository>,
) {
    suspend operator fun invoke(text: String) = repository.value.createPost(text)
}
