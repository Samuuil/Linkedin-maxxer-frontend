package com.linkedinmaxxer.app.ui.feature.home

import com.linkedinmaxxer.app.domain.model.response.DashboardActivityItem

data class HomeUIData(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val pendingSuggestions: Int = 0,
    val activeSubscriptions: Int = 0,
    val autoCommentEnabled: Int = 0,
    val recentGrowthPercent: Int = 0,
    val recentActivity: List<DashboardActivityItem> = emptyList(),
)

sealed interface HomeAction {
    data object OnRefresh : HomeAction
    data object OnErrorShown : HomeAction
}
