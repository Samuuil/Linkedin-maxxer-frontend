package com.linkedinmaxxer.app.domain.usecase.auth

import com.linkedinmaxxer.app.domain.model.request.RegisterModel
import com.linkedinmaxxer.app.domain.repository.AuthRepository

class RegisterUseCase(
    private val authRepository: Lazy<AuthRepository>,
) {
    suspend operator fun invoke(email: String, password: String) =
        authRepository.value.register(RegisterModel(email = email, password = password))
}
