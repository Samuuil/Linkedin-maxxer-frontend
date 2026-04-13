package com.linkedinmaxxer.app.ui.feature.posts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkedinmaxxer.app.domain.usecase.posts.GetPostsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostsViewModel(
    private val getPostsUseCase: GetPostsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(PostsUIData())
    val state = _state.asStateFlow()

    init {
        load(initial = true)
    }

    fun onAction(action: PostsAction) {
        when (action) {
            PostsAction.OnRefresh -> load(initial = false)
            PostsAction.OnErrorShown -> _state.update { it.copy(errorMessage = null) }
            is PostsAction.OnFilterChanged -> {
                _state.update { it.copy(selectedFilter = action.filter) }
                load(initial = false)
            }
        }
    }

    private fun load(initial: Boolean) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = if (initial) true else it.isLoading,
                    isRefreshing = !initial,
                    errorMessage = null,
                )
            }

            getPostsUseCase(_state.value.selectedFilter.backendValue).fold(
                onSuccess = { response ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            posts = response.data,
                            totalPosts = response.meta?.totalItems ?: response.data.size,
                        )
                    }
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isRefreshing = false,
                            errorMessage = throwable.message ?: "Unable to load posts.",
                        )
                    }
                },
            )
        }
    }
}
