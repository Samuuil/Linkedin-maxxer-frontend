package com.linkedinmaxxer.app.ui.feature.subscriptions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkedinmaxxer.app.domain.usecase.subscription.GetSubscriptionsUseCase
import com.linkedinmaxxer.app.domain.usecase.subscription.SubscribeUseCase
import com.linkedinmaxxer.app.domain.usecase.subscription.ToggleAutoCommentUseCase
import com.linkedinmaxxer.app.domain.usecase.subscription.UnsubscribeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SubscriptionsViewModel(
    private val getSubscriptionsUseCase: GetSubscriptionsUseCase,
    private val subscribeUseCase: SubscribeUseCase,
    private val unsubscribeUseCase: UnsubscribeUseCase,
    private val toggleAutoCommentUseCase: ToggleAutoCommentUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(SubscriptionsUIData())
    val state = _state.asStateFlow()

    init {
        load(initial = true)
    }

    fun onAction(action: SubscriptionsAction) {
        when (action) {
            SubscriptionsAction.OnRefresh -> load(initial = false)
            SubscriptionsAction.OnErrorShown -> _state.update { it.copy(errorMessage = null) }
            SubscriptionsAction.OnSuccessShown -> _state.update { it.copy(successMessage = null) }
            is SubscriptionsAction.OnLinkedinUrlChanged -> _state.update { it.copy(linkedinUrlInput = action.value) }
            SubscriptionsAction.OnSubscribeClicked -> subscribe()
            is SubscriptionsAction.OnDeleteSubscription -> removeSubscription(action.subscriptionId)
            is SubscriptionsAction.OnAutoCommentChanged -> setAutoComment(action.subscriptionId, action.enabled)
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

            getSubscriptionsUseCase().fold(
                onSuccess = { subscriptions ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSubmitting = false,
                            subscriptions = subscriptions,
                        )
                    }
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isSubmitting = false,
                            errorMessage = throwable.message ?: "Unable to load subscriptions.",
                        )
                    }
                },
            )
        }
    }

    private fun subscribe() {
        val url = _state.value.linkedinUrlInput.trim()
        if (url.isBlank()) {
            _state.update { it.copy(errorMessage = "LinkedIn username is required.") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true, errorMessage = null, successMessage = null) }
            subscribeUseCase(url).fold(
                onSuccess = {
                    _state.update {
                        it.copy(
                            isSubmitting = false,
                            linkedinUrlInput = "",
                            successMessage = "Subscription added.",
                        )
                    }
                    load(initial = false)
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isSubmitting = false,
                            errorMessage = throwable.message ?: "Unable to subscribe.",
                        )
                    }
                },
            )
        }
    }

    private fun removeSubscription(subscriptionId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isSubmitting = true, errorMessage = null) }
            unsubscribeUseCase(subscriptionId).fold(
                onSuccess = {
                    _state.update { it.copy(isSubmitting = false, successMessage = "Subscription removed.") }
                    load(initial = false)
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isSubmitting = false,
                            errorMessage = throwable.message ?: "Unable to remove subscription.",
                        )
                    }
                },
            )
        }
    }

    private fun setAutoComment(subscriptionId: String, enabled: Boolean) {
        viewModelScope.launch {
            val previous = _state.value.subscriptions
            _state.update {
                it.copy(
                    subscriptions = it.subscriptions.map { item ->
                        if (item.id == subscriptionId) item.copy(autoComment = enabled) else item
                    },
                )
            }
            toggleAutoCommentUseCase(subscriptionId, enabled).fold(
                onSuccess = { },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            subscriptions = previous,
                            errorMessage = throwable.message ?: "Unable to update auto-comment.",
                        )
                    }
                },
            )
        }
    }
}
