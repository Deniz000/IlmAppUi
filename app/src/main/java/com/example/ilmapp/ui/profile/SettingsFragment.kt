package com.example.ilmapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.ilmapp.config.PreferencesManager
import com.example.ilmapp.data.model.AuthViewModel
import com.example.ilmapp.data.model.TokenManager
import com.example.ilmapp.databinding.FragmentSettingsBinding
import com.example.yourapp.views.PasswordEditText

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val authViewModel: AuthViewModel by viewModels()
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

        PreferencesManager.getUserData(requireContext()).let { (username, email, _) ->
            profileUsername.hint = username
            profileEmail.hint = email
        }

        val id = authViewModel.decodeJwtToken(tokenManager.getToken()!!)
        authViewModel.getUserProfile(requireContext(),id)

        return root
    }

}