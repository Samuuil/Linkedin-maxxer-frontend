package com.linkedinmaxxer.app.ui.feature.setup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.linkedinmaxxer.app.domain.usecase.account.SetupLinkedinUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SetupViewModel(
    private val setupLinkedinUseCase: SetupLinkedinUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(SetupUIData())
    val state = _state.asStateFlow()

    fun onAction(action: SetupAction) {
        when (action) {
            is SetupAction.OnOfficialTokenChanged -> _state.update { it.copy(officialToken = action.value) }
            is SetupAction.OnUnofficialTokenChanged -> _state.update { it.copy(unofficialToken = action.value) }
            is SetupAction.OnLinkedinEmailChanged -> _state.update { it.copy(linkedinEmail = action.value) }
            is SetupAction.OnLinkedinPasswordChanged -> _state.update { it.copy(linkedinPassword = action.value) }
            SetupAction.OnContinue -> saveAll()
            SetupAction.OnErrorShown -> _state.update { it.copy(errorMessage = null) }
            SetupAction.OnSuccessShown -> _state.update { it.copy(successMessage = null) }
        }
    }

    private fun saveAll() {
        val officialToken = _state.value.officialToken.trim()
        val unofficialToken = _state.value.unofficialToken.trim()
        val linkedinEmail = _state.value.linkedinEmail.trim()
        val linkedinPassword = _state.value.linkedinPassword

        if (officialToken.isEmpty() || unofficialToken.isEmpty() || linkedinEmail.isEmpty() || linkedinPassword.isEmpty()) {
            _state.update { it.copy(errorMessage = "Fill all LinkedIn fields before continuing.") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isSaving = true, errorMessage = null, successMessage = null) }

            val officialResult = setupLinkedinUseCase.setOfficialToken(officialToken)
            if (officialResult.isFailure) {
                _state.update {
                    it.copy(isSaving = false, errorMessage = officialResult.exceptionOrNull()?.message ?: "Failed to save official token.")
                }
                return@launch
            }

            val unofficialResult = setupLinkedinUseCase.setUnofficialToken(unofficialToken)
            if (unofficialResult.isFailure) {
                _state.update {
                    it.copy(isSaving = false, errorMessage = unofficialResult.exceptionOrNull()?.message ?: "Failed to save unofficial token.")
                }
                return@launch
            }

            val credentialsResult = setupLinkedinUseCase.setLinkedinCredentials(linkedinEmail, linkedinPassword)
            credentialsResult.fold(
                onSuccess = {
                    _state.update {
                        it.copy(
                            isSaving = false,
                            officialConnected = true,
                            unofficialConnected = true,
                            credentialsConnected = true,
                            successMessage = "LinkedIn settings saved.",
                            completed = true,
                        )
                    }
                },
                onFailure = { throwable ->
                    _state.update {
                        it.copy(
                            isSaving = false,
                            errorMessage = throwable.message ?: "Failed to save credentials.",
                        )
                    }
                },
            )
        }
    }
}
