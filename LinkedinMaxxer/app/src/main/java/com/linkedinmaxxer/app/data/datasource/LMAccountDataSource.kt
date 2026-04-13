package com.linkedinmaxxer.app.data.datasource

import com.linkedinmaxxer.app.data.service.AuthService
import com.linkedinmaxxer.app.data.service.UserService
import com.linkedinmaxxer.app.domain.model.request.SetLinkedinCredentialsModel
import com.linkedinmaxxer.app.domain.model.request.SetOfficialTokenModel
import com.linkedinmaxxer.app.domain.model.request.SetUnofficialTokenModel
import com.linkedinmaxxer.app.domain.model.request.UpdatePushTokenModel
import com.linkedinmaxxer.app.domain.model.response.UserProfile

class LMAccountDataSource(
    private val authService: AuthService,
    private val userService: UserService,
) : AccountDataSource {
    override suspend fun profile(): Result<UserProfile> = requestBody(authService.profile())

    override suspend fun logout(): Result<Map<String, String>> = requestBody(authService.logout())

    override suspend fun setOfficialToken(token: String): Result<Unit> =
        requestBody(userService.setOfficialToken(SetOfficialTokenModel(oficialToken = token)))

    override suspend fun setUnofficialToken(token: String): Result<Unit> =
        requestBody(userService.setUnofficialToken(SetUnofficialTokenModel(unofficialToken = token)))

    override suspend fun setLinkedinCredentials(email: String, password: String): Result<Unit> =
        requestBody(
            userService.setLinkedinCredentials(
                SetLinkedinCredentialsModel(linkedinEmail = email, linkedinPassword = password),
            ),
        )

    override suspend fun updatePushToken(pushToken: String): Result<Unit> =
        requestBody(authService.updatePushToken(UpdatePushTokenModel(pushToken = pushToken)))
}
