package com.linkedinmaxxer.app.ui.feature.auth

data class LoginUIData(
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
)

sealed interface LoginAction {
    data class OnEmailChanged(val value: String) : LoginAction
    data class OnPasswordChanged(val value: String) : LoginAction
    data object OnPasswordVisibilityToggle : LoginAction
    data object OnLoginClicked : LoginAction
    data object OnErrorShown : LoginAction
}
