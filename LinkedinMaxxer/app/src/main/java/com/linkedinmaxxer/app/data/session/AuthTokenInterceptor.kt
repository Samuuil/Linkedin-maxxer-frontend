package com.linkedinmaxxer.app.data.session

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val accessToken = runBlocking {
            val cached = SessionManager.getToken()
            if (cached.isNotBlank()) cached else SessionManager.fetchAuthToken()
        }

        if (accessToken.isNotBlank()) {
            requestBuilder.addHeader("Authorization", "Bearer $accessToken")
        }

        return chain.proceed(requestBuilder.build())
    }
}
