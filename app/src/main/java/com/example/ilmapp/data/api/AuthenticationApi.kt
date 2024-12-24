package com.example.ilmapp.data.api

import com.example.ilmapp.data.model.RegisterRequest
import com.example.ilmapp.data.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi {
    @POST("/api/auth/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>
}