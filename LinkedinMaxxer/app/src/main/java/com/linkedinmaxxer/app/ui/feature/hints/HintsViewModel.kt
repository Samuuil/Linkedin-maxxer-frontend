package com.linkedinmaxxer.app.ui.feature.hints

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkedinmaxxer.app.domain.usecase.subscription.GetSuggestionsUseCase
import com.linkedinmaxxer.app.domain.usecase.subscription.RespondSuggestionUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HintsViewModel(
    private val getSuggestionsUseCase: GetSuggestionsUseCase,
    private val respondSuggestionUseCase: RespondSuggestionUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HintsUIData())
    val state = _state.asStateFlow()

    init {
        load(initial = true)
    }

    fun onAction(action: HintsAction) {
        when (action) {
            HintsAction.OnRefresh -> load(initial = false)
            HintsAction.OnErrorShown -> _state.update { it.copy(errorMessage = null) }
            is HintsAction.OnFilterChanged -> {
                _state.update { it.copy(filter = action.filter) }
                load(initial = false)
            }
            is HintsAction.OnRespond -> respond(action.suggestionId, action.approve)
        }
    }

    private fun load(initial: Boolean) {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = if (initial) true else it.isLoading,
                    isSubmitting = !initial,
                    errorMessage = null,
                )
            }
            getSuggestionsUseCase(_state.value.filter.onlyPending).fold(
                onSuccess = { suggestions ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSubmitting = false,
                            suggestions = suggestions,
                        )
                    }
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSubmitting = false,
                            errorMessage = throwable.message ?: "Unable to load suggestions.",
                        )
                    }
                },
            )
        }
    }

    private fun respond(suggestionId: String, approve: Boolean) {
        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true, errorMessage = null) }
            respondSuggestionUseCase(suggestionId, approve).fold(
                onSuccess = { updated ->
                    _state.update {
                        it.copy(
                            isSubmitting = false,
                            suggestions = it.suggestions.map { item ->
                                if (item.id == updated.id) updated else item
                            },
                        )
                    }
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isSubmitting = false,
                            errorMessage = throwable.message ?: "Unable to update suggestion.",
                        )
                    }
                },
            )
        }
    }
}
