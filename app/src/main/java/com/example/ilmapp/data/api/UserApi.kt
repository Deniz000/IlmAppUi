package com.example.ilmapp.data.api

import com.example.ilmapp.data.model.UserProfileResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    @GET("/api/users/{id}")
    fun getUserInformation(@Path("id") userId: Long): Call<UserProfileResponse>
}