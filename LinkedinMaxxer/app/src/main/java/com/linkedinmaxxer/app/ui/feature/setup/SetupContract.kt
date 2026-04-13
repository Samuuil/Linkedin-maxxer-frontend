package com.linkedinmaxxer.app.ui.feature.setup

data class SetupUIData(
    val officialToken: String = "",
    val unofficialToken: String = "",
    val linkedinEmail: String = "",
    val linkedinPassword: String = "",
    val officialConnected: Boolean = false,
    val unofficialConnected: Boolean = false,
    val credentialsConnected: Boolean = false,
    val isSaving: Boolean = false,
    val errorMessage: String? = null,
    val successMessage: String? = null,
    val completed: Boolean = false,
)

sealed interface SetupAction {
    data class OnOfficialTokenChanged(val value: String) : SetupAction
    data class OnUnofficialTokenChanged(val value: String) : SetupAction
    data class OnLinkedinEmailChanged(val value: String) : SetupAction
    data class OnLinkedinPasswordChanged(val value: String) : SetupAction
    data object OnContinue : SetupAction
    data object OnErrorShown : SetupAction
    data object OnSuccessShown : SetupAction
}
