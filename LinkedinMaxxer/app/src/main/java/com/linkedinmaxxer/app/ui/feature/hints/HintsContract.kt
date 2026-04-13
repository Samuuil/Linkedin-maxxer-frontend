package com.linkedinmaxxer.app.ui.feature.hints

import com.linkedinmaxxer.app.domain.model.response.CommentSuggestionResponse

enum class HintsFilter(val onlyPending: Boolean) {
    PENDING(true),
    ALL(false),
}

data class HintsUIData(
    val isLoading: Boolean = true,
    val isSubmitting: Boolean = false,
    val errorMessage: String? = null,
    val filter: HintsFilter = HintsFilter.PENDING,
    val suggestions: List<CommentSuggestionResponse> = emptyList(),
)

sealed interface HintsAction {
    data object OnRefresh : HintsAction
    data class OnFilterChanged(val filter: HintsFilter) : HintsAction
    data class OnRespond(val suggestionId: String, val approve: Boolean) : HintsAction
    data object OnErrorShown : HintsAction
}
