package com.example.ilmapp.data.api

import android.content.Context
import android.util.Log
import com.example.ilmapp.data.model.LoginRequest
import com.example.ilmapp.data.model.RefreshResponse
import com.example.ilmapp.data.model.RefreshTokenRequest
import com.example.ilmapp.data.model.RegisterRequest
import com.example.ilmapp.data.model.TokenManager
import com.example.ilmapp.data.model.UserProfileResponse
import com.example.ilmapp.data.model.UserResponse
import com.example.ilmapp.data.model.UserUpdateRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ApiRepository(private val tokenManager: TokenManager) {
    private val userApiClass: Class<UserApi> = UserApi::class.java

    fun loginUser(
        request: LoginRequest,
        onResponse: (UserResponse?) -> Unit,
        onError: (String) -> Unit
    ) {
        val call = RetrofitInstance.instance.loginUser(request)
        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        tokenManager.saveToken(it.accessToken) // Access token
                        if (it.refreshToken.isNotEmpty()) {
                            tokenManager.saveRefreshToken(it.refreshToken) // Refresh token
                        }
                    }
                    onResponse(response.body())

                } else {
                    onError("Login error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
            }
        })
    }

    fun registerUser(
        request: RegisterRequest,
        onResponse: (UserResponse?) -> Unit,
        onError: (String) -> Unit
    ) {
        val call = RetrofitInstance.instance.registerUser(request)
        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        tokenManager.saveToken(it.accessToken) // Access token
                        if (it.refreshToken.isNotEmpty()) {
                            tokenManager.saveRefreshToken(it.refreshToken) // Refresh token
                        }
                    }
                    onResponse(response.body())
                } else {
                    onError("Register error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                onError("Register failure: ${t.message}")
            }
        })
    }

    fun getUserProfile(
        userId: Long,
        onResponse: (UserProfileResponse?) -> Unit,
        onError: (String) -> Unit
    ) {
        val token = tokenManager.getToken()
        if (token != null) {
            val call = RetrofitInstance.createApi(userApiClass).getUserInformation(userId)
            call.enqueue(object : Callback<UserProfileResponse> {
                override fun onResponse(
                    call: Call<UserProfileResponse>,
                    response: Response<UserProfileResponse>
                ) {
                    if (response.isSuccessful) {
                        onResponse(response.body())
                    } else {
                        onError("Get profile error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                    onError("Get profile failure: ${t.message}")
                }
            })
        } else {
            onError("Token bulunamadı.")
        }
    }

    fun updateUSER(
        userId: Long,
        request: UserUpdateRequest,
        onResponse: (UserProfileResponse?) -> Unit, onError: (String) -> Unit
    ) {
        val token = tokenManager.getToken()
        if (token != null) {
            val call = RetrofitInstance.createApi(userApiClass).updateUser(userId, request)
            call.enqueue(object : Callback<UserProfileResponse> {
                override fun onResponse(
                    call: Call<UserProfileResponse>,
                    response: Response<UserProfileResponse>
                ) {
                    if (response.isSuccessful) {
                        onResponse(response.body())
                    } else {
                        onError("Update user error: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                    onError("Update user failure: ${t.message}")
                }
            })
        } else {
            onError("Token bulunamadı.")
        }
    }

    fun <T> makeAuthenticatedApiCall(
        token: String,
        isExpired: Boolean,
        apiCall: (String) -> Call<T>, // Token gerektiren API çağrısı
        onSuccess: (Response<T>) -> Unit,
        onError: (String) -> Unit
    ) {
        if (isExpired) {
            refreshJwtToken(token) { success, newAccessToken ->
                if (success && !newAccessToken.isNullOrEmpty()) {
                    // Yeni token ile API çağrısı
                    apiCallWithToken(apiCall, newAccessToken, onSuccess, onError)
                } else {
                    onError("Token yenileme başarısız.")
                }
            }
        } else {
            // Token geçerliyse API çağrısını yapılıyor
            apiCallWithToken(apiCall, token, onSuccess, onError)
        }
    }

    private fun <T> apiCallWithToken(
        apiCall: (String) -> Call<T>, // Token gerektiren API çağrısı
        token: String,
        onSuccess: (Response<T>) -> Unit,
        onError: (String) -> Unit
    ) {
        tokenManager.saveToken(token)
        val call = apiCall(token) // Token, API çağrısına parametre olarak iletiliyor
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    onSuccess(response)
                } else {
                    onError("API Hatası: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                onError("Network Hatası: ${t.message}")
            }
        })
    }

    fun refreshJwtToken(token: String, onTokenRefreshed: (Boolean, String?) -> Unit) {
        val request = RefreshTokenRequest(token)
        val apiService = RetrofitInstance.createApi(AuthenticationApi::class.java)

        val call = apiService.refreshToken(request)
        call.enqueue(object : Callback<RefreshResponse> {
            override fun onResponse(
                call: Call<RefreshResponse>,
                response: Response<RefreshResponse>
            ) {
                if (response.isSuccessful) {
                    val newAccessToken = response.body()?.accessToken
                    val newRefreshToken = response.body()?.refreshToken

                    // Yeni Access Token ve Refresh Token'ı kaydet
                    if (!newAccessToken.isNullOrEmpty()) {
                        tokenManager.saveToken(newAccessToken)
                    }
                    if (!newRefreshToken.isNullOrEmpty()) {
                        tokenManager.saveRefreshToken(newRefreshToken)
                    }
                    onTokenRefreshed(true, newAccessToken)
                } else {
                    Log.e("RefreshToken", "Token yenileme başarısız. Kod: ${response.code()}")
                    onTokenRefreshed(false, null)
                }
            }

            override fun onFailure(call: Call<RefreshResponse>, t: Throwable) {
                onTokenRefreshed(false, null)
            }
        })
    }

    fun logout(context: Context, onLogoutSuccess: () -> Unit, onLogoutError: (String) -> Unit) {
        val tokenManager = TokenManager(context)

        // Sunucuda logout isteği (isteğe bağlı)
        val call = RetrofitInstance.createApi(userApiClass).logout()
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    tokenManager.clearTokens()
                    onLogoutSuccess()
                } else {
                    onLogoutError("Logout işlemi başarısız: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                onLogoutError("Network hatası: ${t.message}")
            }
        })
    }


}
