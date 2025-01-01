package com.example.ilmapp.data.model

import android.content.Context
import android.util.Log
import com.auth0.android.jwt.JWT

class TokenManager(context: Context) {
    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) {
        Log.e("Token alınıyor", "Token: $token")
        prefs.edit().putString("jwt_token", token).apply()
    }

    fun saveRefreshToken(refreshToken: String) {
        prefs.edit().putString("refresh_token", refreshToken).apply()
    }

    fun getToken(): String? {
        return prefs.getString("jwt_token", null)
    }

    fun getRefreshToken(): String? {
        return prefs.getString("refresh_token", null)
    }


    fun clearTokens() {
        prefs.edit().remove("jwt_token").remove("refresh_token").apply()
    }



    fun decodeJwtToken(token: String): MutableList<UserData> {
        val userId: Long
        val name: String
        val roles: List<String>
        val isExpired: Boolean
        val list: MutableList<UserData> = mutableListOf()
        try {
            val jwt = JWT(token)

            userId = jwt.getClaim("userId").asLong()!!
            name = jwt.getClaim("name").asString()!!
            roles = jwt.getClaim("role").asList(String::class.java)
            isExpired = jwt.isExpired(24)
            list.add(UserData(userId, name, roles, isExpired))

            println("UserId: $userId")
            println("roles: $roles")
            println("Is token expired? $isExpired")
        } catch (e: Exception) {
            println("Error decoding token: ${e.message}")
        }

        return list
    }

}
