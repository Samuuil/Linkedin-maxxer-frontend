package com.linkedinmaxxer.app.ui.feature.posts.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkedinmaxxer.app.domain.usecase.posts.CreatePostUseCase
import com.linkedinmaxxer.app.domain.usecase.posts.EnhanceDescriptionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreatePostViewModel(
    private val createPostUseCase: CreatePostUseCase,
    private val enhanceDescriptionUseCase: EnhanceDescriptionUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(CreatePostUIData())
    val state = _state.asStateFlow()

    fun onAction(action: CreatePostAction) {
        when (action) {
            is CreatePostAction.OnTextChanged -> _state.update {
                it.copy(
                    text = action.value.take(it.maxLength),
                    publishCompleted = false,
                    successMessage = null,
                )
            }
            CreatePostAction.OnEnhanceClicked -> enhanceText()
            CreatePostAction.OnApplyEnhancementClicked -> {
                val enhanced = _state.value.enhancedText ?: return
                _state.update { it.copy(text = enhanced, enhancedText = null) }
            }
            CreatePostAction.OnPublishClicked -> publishPost()
            CreatePostAction.OnErrorShown -> _state.update { it.copy(errorMessage = null) }
            CreatePostAction.OnSuccessShown -> _state.update { it.copy(successMessage = null) }
        }
    }

    private fun enhanceText() {
        val content = _state.value.text.trim()
        if (content.isEmpty()) {
            _state.update { it.copy(errorMessage = "Write something before AI enhancement.") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isEnhancing = true, errorMessage = null) }
            enhanceDescriptionUseCase(content).fold(
                onSuccess = { response ->
                    _state.update { it.copy(isEnhancing = false, enhancedText = response.description) }
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isEnhancing = false,
                            errorMessage = throwable.message ?: "Unable to enhance text.",
                        )
                    }
                },
            )
        }
    }

    private fun publishPost() {
        val content = _state.value.text.trim()
        if (content.isEmpty()) {
            _state.update { it.copy(errorMessage = "Post text cannot be empty.") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isPublishing = true, errorMessage = null, successMessage = null, publishCompleted = false) }
            createPostUseCase(content).fold(
                onSuccess = {
                    _state.update {
                        it.copy(
                            isPublishing = false,
                            successMessage = "Post submitted.",
                            publishCompleted = true,
                        )
                    }
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isPublishing = false,
                            errorMessage = throwable.message ?: "Unable to create post.",
                        )
                    }
                },
            )
        }
    }
}
