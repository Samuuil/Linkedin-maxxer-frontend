package com.linkedinmaxxer.app.data.service

import com.linkedinmaxxer.app.data.constants.User
import com.linkedinmaxxer.app.domain.model.request.SetLinkedinCredentialsModel
import com.linkedinmaxxer.app.domain.model.request.SetOfficialTokenModel
import com.linkedinmaxxer.app.domain.model.request.SetUnofficialTokenModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST(User.OFFICIAL_TOKEN)
    suspend fun setOfficialToken(@Body body: SetOfficialTokenModel): Response<Unit>

    @POST(User.UNOFFICIAL_TOKEN)
    suspend fun setUnofficialToken(@Body body: SetUnofficialTokenModel): Response<Unit>

    @POST(User.LINKEDIN_CREDENTIALS)
    suspend fun setLinkedinCredentials(@Body body: SetLinkedinCredentialsModel): Response<Unit>
}
