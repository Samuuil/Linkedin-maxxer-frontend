package com.linkedinmaxxer.app.domain.usecase.posts

import com.linkedinmaxxer.app.domain.repository.PostsRepository

class EnhanceDescriptionUseCase(
    private val repository: Lazy<PostsRepository>,
) {
    suspend operator fun invoke(description: String) = repository.value.enhanceDescription(description)
}
