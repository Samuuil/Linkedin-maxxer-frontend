package com.linkedinmaxxer.app.ui.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkedinmaxxer.app.data.session.SessionManager
import com.linkedinmaxxer.app.domain.usecase.account.GetProfileUseCase
import com.linkedinmaxxer.app.domain.usecase.account.LogoutUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsUIData())
    val state = _state.asStateFlow()

    init {
        load()
    }

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnPushNotificationsChanged -> {
                // Backend only accepts pushToken string, not a true/false preference.
                _state.update { it.copy(pushNotificationsEnabled = action.value) }
            }
            SettingsAction.OnLogoutClicked -> logout()
            SettingsAction.OnErrorShown -> _state.update { it.copy(errorMessage = null) }
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
