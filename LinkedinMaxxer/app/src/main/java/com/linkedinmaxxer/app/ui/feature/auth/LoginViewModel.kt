package com.linkedinmaxxer.app.ui.feature.auth

import android.content.Context
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkedinmaxxer.app.data.session.SessionManager
import com.linkedinmaxxer.app.data.session.dataStore
import com.linkedinmaxxer.app.domain.usecase.auth.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    context: Context,
) : ViewModel() {
    private val _state = MutableStateFlow(LoginUIData())
    val state = _state.asStateFlow()

    init {
        SessionManager.initialize(context.dataStore)
    }

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnEmailChanged -> _state.update { it.copy(email = action.value) }
            is LoginAction.OnPasswordChanged -> _state.update { it.copy(password = action.value) }
            LoginAction.OnPasswordVisibilityToggle -> _state.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
            LoginAction.OnLoginClicked -> login()
            LoginAction.OnErrorShown -> _state.update { it.copy(errorMessage = null) }
        }
    }

    private fun login() {
        val current = _state.value
        val email = current.email.trim()
        val password = current.password

        val validationError = when {
            email.isEmpty() -> "Email is required."
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Enter a valid email address."
            password.isEmpty() -> "Password is required."
            else -> null
        }

        if (validationError != null) {
            _state.update { it.copy(errorMessage = validationError) }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            loginUseCase(email = email, password = password).fold(
                onSuccess = { token ->
                    SessionManager.setAuthEntities(token.accessToken, token.refreshToken)
                    _state.update { it.copy(isLoading = false, isSuccess = true) }
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = throwable.message ?: "Unable to log in.",
                        )
                    }
                },
            )
        }
    }
}
