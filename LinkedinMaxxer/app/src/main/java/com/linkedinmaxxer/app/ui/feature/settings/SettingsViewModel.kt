package com.linkedinmaxxer.app.ui.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.linkedinmaxxer.app.data.session.SessionManager
import com.linkedinmaxxer.app.domain.usecase.account.GetProfileUseCase
import com.linkedinmaxxer.app.domain.usecase.account.LogoutUseCase
import com.linkedinmaxxer.app.domain.usecase.account.UpdatePushTokenUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class SettingsViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val updatePushTokenUseCase: UpdatePushTokenUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsUIData())
    val state = _state.asStateFlow()

    init {
        load()
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnPushNotificationsChanged -> {
                if (action.value) enablePushNotifications() else disablePushNotifications()
            }
            SettingsAction.OnLogoutClicked -> logout()
            SettingsAction.OnErrorShown -> _state.update { it.copy(errorMessage = null) }
        }
    }

    private fun enablePushNotifications() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val token = FirebaseMessaging.getInstance().token.await()
                updatePushTokenUseCase(token).fold(
                    onSuccess = { _state.update { it.copy(isLoading = false, pushNotificationsEnabled = true) } },
                    onFailure = { throwable ->
                        _state.update {
                            it.copy(isLoading = false, errorMessage = throwable.message ?: "Failed to enable notifications.")
                        }
                    },
                )
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = "Failed to get notification token.") }
            }
        }
    }

    private fun disablePushNotifications() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            updatePushTokenUseCase("").fold(
                onSuccess = { _state.update { it.copy(isLoading = false, pushNotificationsEnabled = false) } },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(isLoading = false, errorMessage = throwable.message ?: "Failed to disable notifications.")
                    }
                },
            )
        }
    }

    private fun load() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            getProfileUseCase().fold(
                onSuccess = { profile ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            profile = profile,
                            pushNotificationsEnabled = !profile.pushToken.isNullOrBlank(),
                        )
                    }
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "Unable to load profile.",
                        )
                    }
                },
            )
        }
    }

    private fun logout() {
        viewModelScope.launch {
            logoutUseCase().fold(
                onSuccess = {
                    SessionManager.setAuthEntities("", "")
                    _state.update { it.copy(logoutCompleted = true) }
                },
                onFailure = { throwable ->
                    _state.update { it.copy(errorMessage = throwable.message ?: "Logout failed.") }
                },
            )
        }
    }
}
