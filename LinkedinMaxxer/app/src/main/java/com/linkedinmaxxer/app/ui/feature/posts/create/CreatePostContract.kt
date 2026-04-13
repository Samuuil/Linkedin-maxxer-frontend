package com.linkedinmaxxer.app.ui.feature.posts.create

data class CreatePostUIData(
    val text: String = "",
    val enhancedText: String? = null,
    val isEnhancing: Boolean = false,
    val isPublishing: Boolean = false,
    val publishCompleted: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val maxLength: Int = 3000,
)

sealed interface CreatePostAction {
    data class OnTextChanged(val value: String) : CreatePostAction
    data object OnEnhanceClicked : CreatePostAction
    data object OnApplyEnhancementClicked : CreatePostAction
    data object OnPublishClicked : CreatePostAction
    data object OnErrorShown : CreatePostAction
    data object OnSuccessShown : CreatePostAction
}
