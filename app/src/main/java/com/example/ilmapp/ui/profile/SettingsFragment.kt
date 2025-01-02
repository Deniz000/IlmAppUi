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
    private lateinit var passwordEditText: PasswordEditText
    private var isPasswordVisible = false
    private lateinit var tokenManager: TokenManager

    private fun initializeComponents() {
        tokenManager = TokenManager(requireContext())
        val factory = AuthViewModelFactory(requireContext())
        authViewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)
        passwordEditText = PasswordEditText(requireContext(), null)
    }

    private fun setupUserProfile() {
        PreferencesManager.getUserData(requireContext()).let { (username, email, _) ->
            binding.profileUsername.hint = username
            binding.profileEmail.hint = email
        }
    }

    private fun getUserIdFromToken(): Pair<Long, Boolean> {
        val list: MutableList<UserData> = tokenManager.decodeJwtToken(tokenManager.getToken()!!)
        val id = list[0].userId
        val isExpired = list[0].isExpired
        return Pair(id, isExpired)
    }

    private fun updateUserProfile(id: Long, isExpired: Boolean) {
        val updatedRequest = UserUpdateRequest(
            id = id,
            userName = binding.profileUsername.text.toString().trim(),
            email = binding.profileEmail.text.toString().trim(),
            password = binding.profilePassword.text.toString().trim()
        )

        Log.e("Profile", "Updating ID: $id")
        RetrofitInstance.init(requireContext())
        authViewModel.updateUsera(updatedRequest, isExpired)
        Log.e("Profile", "Updated ID: $id")
    }

    private fun onSaveButtonClick() {
        val (id, isExpired) = getUserIdFromToken()

        binding.profileBtnSave.setOnClickListener {
            updateUserProfile(id, isExpired)
            findNavController().navigateUp()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root = binding.root

        initializeComponents()
        setupUserProfile()

        passwordEditText.setPasswordVisibility(binding.profilePassword, isPasswordVisible)
        onSaveButtonClick()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
