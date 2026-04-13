package com.linkedinmaxxer.app.ui.feature.hints

import com.linkedinmaxxer.app.domain.model.response.CommentSuggestionResponse

data class SuggestionReviewUIData(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val suggestion: CommentSuggestionResponse? = null,
)

sealed interface SuggestionReviewAction {
    data class OnLoad(val suggestionId: String) : SuggestionReviewAction
    data class OnRespond(val approve: Boolean) : SuggestionReviewAction
    data object OnErrorShown : SuggestionReviewAction
}
