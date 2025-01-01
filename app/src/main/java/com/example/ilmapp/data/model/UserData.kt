package com.example.ilmapp.data.model

data class UserData(
    val userId: Long,
    val name: String,
    val roles: List<String>,
    val isExpired: Boolean
)
