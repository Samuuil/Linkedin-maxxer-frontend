package com.linkedinmaxxer.app.domain.model.response

import kotlinx.serialization.Serializable

@Serializable
data class DashboardSummary(
    val pendingSuggestions: Int,
    val activeSubscriptions: Int,
    val autoCommentEnabled: Int,
    val recentGrowthPercent: Int,
    val recentActivity: List<DashboardActivityItem>,
)
