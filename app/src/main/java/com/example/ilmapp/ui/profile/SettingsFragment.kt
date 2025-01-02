package com.example.ilmapp.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.ilmapp.config.PreferencesManager
import com.example.ilmapp.data.api.RetrofitInstance
import com.example.ilmapp.data.model.AuthViewModel
import com.example.ilmapp.data.model.AuthViewModelFactory
import com.example.ilmapp.data.model.TokenManager
import com.example.ilmapp.data.model.UserData
import com.example.ilmapp.data.model.UserUpdateRequest
import com.example.ilmapp.databinding.FragmentSettingsBinding
import com.example.yourapp.views.PasswordEditText

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var authViewModel: AuthViewModel
    private lateinit var password: PasswordEditText
    private var isPasswordVisible = false
    private lateinit var tokenManager: TokenManager


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        tokenManager = TokenManager(requireContext())
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        val root: View = binding.root
        val profileUsername = binding.profileUsername
        val profileEmail = binding.profileEmail
        val profilePassword = binding.profilePassword
        password = PasswordEditText(requireContext(), null)
        password.setPasswordVisibility(profilePassword, isPasswordVisible)
        val btnSave = binding.profileBtnSave

        // ViewModelFactory ile ViewModel'i oluştur
        val factory = AuthViewModelFactory(requireContext())
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]


        PreferencesManager.getUserData(requireContext()).let { (username, email, _) ->
            profileUsername.hint = username
            profileEmail.hint = email
        }

        val list: MutableList<UserData>  = tokenManager.decodeJwtToken(tokenManager.getToken()!!)
        val id = list[0].userId
        val isExpired = list[0].isExpired
        btnSave.setOnClickListener{
            val registerRequest = UserUpdateRequest(
                id= id,
                userName = profileUsername.text.toString().trim() ?: profileUsername.hint,
                email = profileEmail.text.toString().trim(),
                password = profilePassword.text.toString().trim(),
            )
            Log.e("Profile", "Updating ID: $id")
            RetrofitInstance.init(requireContext())
            authViewModel.updateUsera(registerRequest, isExpired)
            Log.e("Profile", "Updated ID: $id")
            findNavController().navigateUp()

        }

        return root
    }

}