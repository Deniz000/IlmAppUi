package com.example.ilmapp.data.model

import com.google.gson.annotations.SerializedName

data class TokensResponse(
    @SerializedName("access_token")
    val accessToken:String,
    @SerializedName("refresh_token")
    val refreshToken: String
)