package com.example.ilmapp.data.model

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val passwordAgain: String,
    val role: String
)
