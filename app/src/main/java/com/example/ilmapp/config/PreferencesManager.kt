package com.example.ilmapp.config

import android.content.Context

object PreferencesManager {

    private const val USER_PREFS_NAME = "UserPrefs"
    private const val SESSION_PREFS_NAME = "SessionPrefs"

    // Kullanıcı Bilgisi Anahtarları
    private const val USERNAME_KEY = "username"
    private const val EMAIL_KEY = "email"
    private const val ROLE_KEY = "role"

    // Oturum Bilgisi Anahtarları
    private const val AUTH_TOKEN_KEY = "authToken"
    private const val IS_LOGGED_IN_KEY = "isLoggedIn"

    // Kullanıcı Bilgileri Kaydetme
    fun saveUserData(context: Context, username: String, email: String, role: String) {
        val prefs = context.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putString(USERNAME_KEY, username)
            putString(EMAIL_KEY, email)
            putString(ROLE_KEY, role)
            apply()
        }
    }

    // Kullanıcı Bilgileri Alma
    fun getUserData(context: Context): Triple<String, String, String> {
        val prefs = context.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE)
        val username = prefs.getString(USERNAME_KEY, "Unknown User") ?: "Unknown User"
        val email = prefs.getString(EMAIL_KEY, "Unknown Email") ?: "Unknown Email"
        val role = prefs.getString(ROLE_KEY, "Unknown Role") ?: "Unknown Role"
        return Triple(username, email, role)
    }

    // Oturum Bilgisi Kaydetme
    fun saveSessionData(context: Context, authToken: String, isLoggedIn: Boolean) {
        val prefs = context.getSharedPreferences(SESSION_PREFS_NAME, Context.MODE_PRIVATE)
        with(prefs.edit()) {
            putString(AUTH_TOKEN_KEY, authToken)
            putBoolean(IS_LOGGED_IN_KEY, isLoggedIn)
            apply()
        }
    }

    // Oturum Bilgisi Alma
    fun getSessionData(context: Context): Pair<String?, Boolean> {
        val prefs = context.getSharedPreferences(SESSION_PREFS_NAME, Context.MODE_PRIVATE)
        val authToken = prefs.getString(AUTH_TOKEN_KEY, null)
        val isLoggedIn = prefs.getBoolean(IS_LOGGED_IN_KEY, false)
        return Pair(authToken, isLoggedIn)
    }

    // Kullanıcı ve Oturum Bilgilerini Temizleme
    fun clearAllData(context: Context) {
        val userPrefs = context.getSharedPreferences(USER_PREFS_NAME, Context.MODE_PRIVATE)
        val sessionPrefs = context.getSharedPreferences(SESSION_PREFS_NAME, Context.MODE_PRIVATE)
        userPrefs.edit().clear().apply()
        sessionPrefs.edit().clear().apply()
    }
}
