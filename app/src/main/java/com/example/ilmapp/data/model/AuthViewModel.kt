package com.example.ilmapp.data.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ilmapp.data.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class AuthViewModel : ViewModel() {
    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse: LiveData<RegisterResponse> get() = _registerResponse

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun postData(request: RegisterRequest) {
        val call = RetrofitClient.instance.registerUser(request)

        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                Log.d("RegisterResponse", "Response: ${response.body()}")
                if (response.isSuccessful) {
                    _registerResponse.value = response.body() // Yanıtı gözlemlemek için
                } else {
                    _error.value = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _error.value = "Failure: ${t.message} $call"
            }
        })
    }

}