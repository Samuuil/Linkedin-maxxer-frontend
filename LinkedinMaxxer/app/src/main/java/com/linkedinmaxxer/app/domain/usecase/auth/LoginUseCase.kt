package com.linkedinmaxxer.app.domain.usecase.auth

import com.linkedinmaxxer.app.domain.model.request.LoginModel
import com.linkedinmaxxer.app.domain.repository.AuthRepository

class LoginUseCase(
    private val authRepository: Lazy<AuthRepository>,
) {
    suspend operator fun invoke(email: String, password: String) =
        authRepository.value.login(LoginModel(email = email, password = password))
}
