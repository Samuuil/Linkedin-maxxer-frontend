package com.linkedinmaxxer.app.data.session

import androidx.datastore.core.DataStore
import androidx.datastore.core.updateData
import kotlinx.coroutines.flow.first

object SessionManager {
    private var dataStore: DataStore<AppSettings>? = null
    private var tokenCache: String = ""
    private var refreshTokenCache: String = ""

    fun initialize(dataStore: DataStore<AppSettings>) {
        this.dataStore = dataStore
    }

    suspend fun setAuthEntities(token: String, refreshToken: String) {
        dataStore?.updateData {
            it.copy(token = token, refreshToken = refreshToken)
        }
        tokenCache = token
        refreshTokenCache = refreshToken
    }

    suspend fun setTokens() {
        tokenCache = fetchAuthToken()
        refreshTokenCache = fetchRefreshToken()
    }

    suspend fun fetchAuthToken(): String = dataStore?.data?.first()?.token.orEmpty()

    suspend fun fetchRefreshToken(): String = dataStore?.data?.first()?.refreshToken.orEmpty()

    fun getToken(): String = tokenCache

    fun getRefreshToken(): String = refreshTokenCache
}
