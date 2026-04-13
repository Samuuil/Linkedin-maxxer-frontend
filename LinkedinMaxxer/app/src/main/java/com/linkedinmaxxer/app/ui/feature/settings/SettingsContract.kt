package com.linkedinmaxxer.app.ui.feature.settings

import com.linkedinmaxxer.app.domain.model.response.UserProfile

data class SettingsUIData(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val profile: UserProfile? = null,
    val pushNotificationsEnabled: Boolean = false,
    val logoutCompleted: Boolean = false,
)

sealed interface SettingsAction {
    data class OnPushNotificationsChanged(val value: Boolean) : SettingsAction
    data object OnLogoutClicked : SettingsAction
    data object OnErrorShown : SettingsAction
}
