package com.example.ilmapp.data.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.auth0.android.jwt.JWT
import com.example.ilmapp.data.api.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthViewModel : ViewModel() {
    private val _userId = MutableLiveData<Long>()
    val id: LiveData<Long> get() = _userId

    private val _response = MutableLiveData<UserResponse>()
    val response: LiveData<UserResponse> get() = _response


    private val _userInformation = MutableLiveData<UserProfileResponse>()
    val userInformation: LiveData<UserProfileResponse> get() = _userInformation



    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun postData(request: RegisterRequest) {
        val call = RetrofitInstance.instance.registerUser(request)

        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                Log.d("RegisterResponse", "Response: ${response.body()}")
                if (response.isSuccessful) {
                    _response.value = response.body()
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _error.value = "Failure: ${t.message} $call"
            }
        })
    }fun loginUser(request: LoginRequest) {
        val call = RetrofitInstance.instance.loginUser(request)

        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                if (response.isSuccessful) {
                    _response.value = response.body()

                } else {
                    _error.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _error.value = "Failure: ${t.message} $call"
            }
        })
    }



    fun getUserProfile(context: Context, uId: Long) {
        Log.e("Profile", "U******* ID: $uId")
        RetrofitInstance.init(context)
        val call = RetrofitInstance.authenticatedApi.getUserInformation(uId)
        Log.e("Profile", "User ID: $call")

        call.enqueue(object : Callback<UserProfileResponse> {
            override fun onResponse(call: Call<UserProfileResponse>, response: Response<UserProfileResponse>) {
                if (response.isSuccessful) {
                    _userInformation.value = response.body()
                    Log.e("Profile", "BAK BURA ${_userInformation.value}")
                } else {
                    Log.e("Profile", "Failed to get user profile: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                Log.e("Profile", "Error: ${t.message}")
            }
        })
    }

    fun decodeJwtToken(token: String): Long {
        var userId:Long = 0
        try {
            val jwt = JWT(token)
            val issuer = jwt.issuer
            val subject = jwt.subject
            val isExpired = jwt.isExpired(10)

            userId = jwt.getClaim("userId").asLong()!!
//        val roles: List<String> = jwt.getClaim("role").asList(String::class.java)

            println("Issuer: $issuer")
            println("Subject: $subject")
            println("UserId: $userId")
            println("Is token expired? $isExpired")
        } catch (e: Exception) {
            println("Error decoding token: ${e.message}")
        }

        return userId
    }

}