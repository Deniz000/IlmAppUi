package com.example.ilmapp.data.model

data class UserUpdateRequest (
    val id:Long,
    val userName: String,
    val email: String,
    val password: String
)
