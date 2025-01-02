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
        val list: MutableList<UserData> = mutableListOf()
        try {
            val jwt = JWT(token)

            val userId = jwt.getClaim("userId").asLong()!!
            val name = jwt.getClaim("name").asString()!!
            val isExpired = jwt.isExpired(24)
            val rolesJson = jwt.getClaim("role").asArray(Role::class.java)
            var firstRole = ""
            if (rolesJson != null && rolesJson.isNotEmpty()) {
                firstRole = rolesJson[0].authority
                Log.e("Role","First role authority: $firstRole")
            } else {
                Log.e("Role","Role listesi boş veya null")
            }

            list.add(UserData(userId, name, firstRole, isExpired))

            println("UserId: $userId")
            println("roles: $firstRole")
            println("Is token expired? $isExpired")
        } catch (e: Exception) {
            println("Error decoding token: ${e.message}")
        }

        return list
    }

}
