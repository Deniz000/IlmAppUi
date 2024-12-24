package com.example.ilmapp.data.model

data class RegisterRequest(
    val userName: String,
    val email: String,
    val password: String,
    val matchingPassword: String,
    val role: String
)
