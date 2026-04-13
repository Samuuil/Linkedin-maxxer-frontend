package com.linkedinmaxxer.app.ui.feature.auth

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkedinmaxxer.app.data.session.SessionManager
import com.linkedinmaxxer.app.data.session.dataStore
import com.linkedinmaxxer.app.domain.usecase.auth.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
    context: Context,
) : ViewModel() {
    private val _state = MutableStateFlow(RegisterUIData())
    val state = _state.asStateFlow()

    init {
        SessionManager.initialize(context.dataStore)
    }

    fun onAction(action: RegisterAction) {
        when (action) {
            is RegisterAction.OnEmailChanged -> _state.update { it.copy(email = action.value) }
            is RegisterAction.OnPasswordChanged -> _state.update { it.copy(password = action.value) }
            is RegisterAction.OnConfirmPasswordChanged -> _state.update { it.copy(confirmPassword = action.value) }
            is RegisterAction.OnTermsChanged -> _state.update { it.copy(agreedToTerms = action.value) }
            RegisterAction.OnPasswordVisibilityToggle -> _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            RegisterAction.OnConfirmPasswordVisibilityToggle -> _state.update {
                it.copy(
                    isConfirmPasswordVisible = !it.isConfirmPasswordVisible,
                )
            }
            RegisterAction.OnRegisterClicked -> register()
            RegisterAction.OnErrorShown -> _state.update { it.copy(errorMessage = null) }
        }
    }

    private fun register() {
        val current = _state.value
        val email = current.email.trim()

        val validationError = when {
            email.isEmpty() -> "Work email is required."
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Enter a valid email address."
            current.password.length < 8 -> "Password must include at least 8 characters."
            current.password != current.confirmPassword -> "Passwords do not match."
            !current.agreedToTerms -> "Please agree to Terms of Service and Privacy Policy."
            else -> null
        }

        if (validationError != null) {
            _state.update { it.copy(errorMessage = validationError) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            registerUseCase(email = email, password = current.password).fold(
                onSuccess = { tokens ->
                    SessionManager.setAuthEntities(tokens.accessToken, tokens.refreshToken)
                    _state.update { it.copy(isLoading = false, isSuccess = true) }
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "Unable to create account.",
                        )
                    }
                },
            )
        }
    }
}
