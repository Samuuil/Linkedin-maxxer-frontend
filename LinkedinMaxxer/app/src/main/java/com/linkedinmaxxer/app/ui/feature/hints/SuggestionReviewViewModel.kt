package com.linkedinmaxxer.app.ui.feature.hints

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkedinmaxxer.app.domain.usecase.subscription.GetSuggestionsUseCase
import com.linkedinmaxxer.app.domain.usecase.subscription.RespondSuggestionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SuggestionReviewViewModel(
    private val getSuggestionsUseCase: GetSuggestionsUseCase,
    private val respondSuggestionUseCase: RespondSuggestionUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(SuggestionReviewUIData())
    val state = _state.asStateFlow()

    private var currentSuggestionId: String? = null

    fun onAction(action: SuggestionReviewAction) {
        when (action) {
            SuggestionReviewAction.OnErrorShown -> _state.update { it.copy(errorMessage = null) }
            is SuggestionReviewAction.OnLoad -> {
                if (currentSuggestionId != action.suggestionId) {
                    currentSuggestionId = action.suggestionId
                    loadSuggestion(action.suggestionId)
                }
            }
            is SuggestionReviewAction.OnRespond -> respond(action.approve)
        }
    }

    private fun loadSuggestion(suggestionId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            getSuggestionsUseCase(onlyPending = false).fold(
                onSuccess = { suggestions ->
                    val suggestion = suggestions.firstOrNull { it.id == suggestionId }
                    _state.update {
                        it.copy(
                            isLoading = false,
                            suggestion = suggestion,
                            errorMessage = if (suggestion == null) "Suggestion not found." else null,
                        )
                    }
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "Unable to load suggestion.",
                        )
                    }
                },
            )
        }
    }

    private fun respond(approve: Boolean) {
        val suggestion = _state.value.suggestion ?: return
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            respondSuggestionUseCase(suggestion.id, approve).fold(
                onSuccess = { updated ->
                    _state.update { it.copy(isLoading = false, suggestion = updated) }
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "Unable to respond to suggestion.",
                        )
                    }
                },
            )
        }
    }
}
