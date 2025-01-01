package com.example.ilmapp.data.model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ilmapp.data.api.ApiRepository
import com.example.ilmapp.data.api.RetrofitInstance
import com.example.ilmapp.data.api.UserApi

class AuthViewModel(context: Context) : ViewModel() {
    private val tokenManager: TokenManager = TokenManager(context)
    private val apiRepository: ApiRepository = ApiRepository(tokenManager)

    private val _userId = MutableLiveData<Long>()
    val id: LiveData<Long> get() = _userId

    private val _response = MutableLiveData<String?>()
    val response: MutableLiveData<String?> get() = _response


    private val _userInformation = MutableLiveData<UserProfileResponse>()
    val userInformation: LiveData<UserProfileResponse> get() = _userInformation


    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun logOut(context: Context){
        apiRepository.logout(context,
            onLogoutSuccess = {},
            onLogoutError = { error -> _error.value = error })
    }
    fun register(request: RegisterRequest) {
        apiRepository.registerUser(request,
            onResponse = { response -> _response.value = response?.accessToken },
            onError = { error -> _error.value = error })
    }

    fun login(request: LoginRequest) {
        apiRepository.loginUser(request,
            onResponse = { response -> _response.value = response?.accessToken },
            onError = { error -> _error.value = error })
    }


    fun getProfile(userId: Long) {
        apiRepository.getUserProfile(userId,
            onResponse = { profile -> _userInformation.value = profile!! },
            onError = { error -> _error.value = error })
    }

    fun updateUser(userId: Long, request: UserUpdateRequest) {
        apiRepository.updateUSER(userId,
            request,
            onResponse = { profile -> _userInformation.value = profile!! },
            onError = { error -> _error.value = error })
    }
    fun updateUsera(userId: Long, request: UserUpdateRequest, isExpired: Boolean) {
        apiRepository.makeAuthenticatedApiCall(
            token = tokenManager.getToken() ?: "",
            isExpired = isExpired,
            apiCall = { RetrofitInstance.createApi(UserApi::class.java)
                .updateUser(userId, request) },
            onSuccess = { profile -> _userInformation.value = profile.body()!! },
            onError = { error -> _error.value = error }
        )
    }
}