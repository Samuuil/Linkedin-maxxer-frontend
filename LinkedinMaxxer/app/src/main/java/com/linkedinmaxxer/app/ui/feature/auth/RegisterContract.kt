package com.linkedinmaxxer.app.ui.feature.auth

data class RegisterUIData(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val agreedToTerms: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false,
)

sealed interface RegisterAction {
    data class OnEmailChanged(val value: String) : RegisterAction
    data class OnPasswordChanged(val value: String) : RegisterAction
    data class OnConfirmPasswordChanged(val value: String) : RegisterAction
    data class OnTermsChanged(val value: Boolean) : RegisterAction
    data object OnPasswordVisibilityToggle : RegisterAction
    data object OnConfirmPasswordVisibilityToggle : RegisterAction
    data object OnRegisterClicked : RegisterAction
    data object OnErrorShown : RegisterAction
}
