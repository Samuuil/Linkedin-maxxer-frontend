package com.linkedinmaxxer.app.data.service

import com.linkedinmaxxer.app.data.constants.Auth
import com.linkedinmaxxer.app.domain.model.request.LoginModel
import com.linkedinmaxxer.app.domain.model.request.RegisterModel
import com.linkedinmaxxer.app.domain.model.response.AuthTokens
import com.linkedinmaxxer.app.domain.model.response.UserProfile
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @POST(Auth.LOGIN)
    suspend fun login(@Body body: LoginModel): Response<AuthTokens>

    @POST(Auth.REGISTER)
    suspend fun register(@Body body: RegisterModel): Response<AuthTokens>

    @GET(Auth.REFRESH)
    suspend fun refresh(): Response<AuthTokens>

    @GET(Auth.PROFILE)
    suspend fun profile(): Response<UserProfile>

    @POST(Auth.LOGOUT)
    suspend fun logout(): Response<Map<String, String>>
}
