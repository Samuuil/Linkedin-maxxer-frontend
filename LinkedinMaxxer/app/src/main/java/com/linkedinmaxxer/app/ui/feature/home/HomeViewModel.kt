package com.linkedinmaxxer.app.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkedinmaxxer.app.domain.usecase.dashboard.GetDashboardSummaryUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getDashboardSummaryUseCase: GetDashboardSummaryUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUIData())
    val state = _state.asStateFlow()

    init {
        load()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            HomeAction.OnRefresh -> load()
            HomeAction.OnErrorShown -> _state.update { it.copy(errorMessage = null) }
        }
    }

    private fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            getDashboardSummaryUseCase().fold(
                onSuccess = { response ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            pendingSuggestions = response.pendingSuggestions,
                            activeSubscriptions = response.activeSubscriptions,
                            autoCommentEnabled = response.autoCommentEnabled,
                            recentGrowthPercent = response.recentGrowthPercent,
                            recentActivity = response.recentActivity,
                        )
                    }
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "Unable to load dashboard.",
                        )
                    }
                },
            )
        }
    }
}
