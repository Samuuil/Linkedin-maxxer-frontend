package com.linkedinmaxxer.app.ui.feature.posts

import com.linkedinmaxxer.app.domain.model.response.PostResponse

enum class PostsFilter(val backendValue: String?) {
    ALL(null),
    PUBLISHED("PUBLISHED"),
    DRAFT("DRAFT"),
    FAILED("FAILED"),
}

data class PostsUIData(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val posts: List<PostResponse> = emptyList(),
    val totalPosts: Int = 0,
    val selectedFilter: PostsFilter = PostsFilter.ALL,
)

sealed interface PostsAction {
    data object OnRefresh : PostsAction
    data class OnFilterChanged(val filter: PostsFilter) : PostsAction
    data object OnErrorShown : PostsAction
}
