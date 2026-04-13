package com.linkedinmaxxer.app.ui.feature.subscriptions

import com.linkedinmaxxer.app.domain.model.response.SubscriptionResponse

data class SubscriptionsUIData(
    val isLoading: Boolean = true,
    val isSubmitting: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val linkedinUrlInput: String = "",
    val subscriptions: List<SubscriptionResponse> = emptyList(),
)

sealed interface SubscriptionsAction {
    data object OnRefresh : SubscriptionsAction
    data class OnLinkedinUrlChanged(val value: String) : SubscriptionsAction
    data object OnSubscribeClicked : SubscriptionsAction
    data class OnDeleteSubscription(val subscriptionId: String) : SubscriptionsAction
    data class OnAutoCommentChanged(val subscriptionId: String, val enabled: Boolean) : SubscriptionsAction
    data object OnErrorShown : SubscriptionsAction
    data object OnSuccessShown : SubscriptionsAction
}
