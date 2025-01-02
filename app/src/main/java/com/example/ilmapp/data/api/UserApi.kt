package com.example.ilmapp.data.api

import com.example.ilmapp.data.model.UserProfileResponse
import com.example.ilmapp.data.model.UserUpdateRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApi {
    @GET("/api/users/{id}")
    fun getUserInformation(@Path("id") userId: Long): Call<UserProfileResponse>

    @PUT("/api/users/update")
    fun updateUser(@Body request: UserUpdateRequest): Call<UserProfileResponse>

    @POST("/api/auth/logout")
    fun logout(): Call<Void>
}