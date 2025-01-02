package com.example.ilmapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dd.processbutton.iml.ActionProcessButton
import com.example.ilmapp.R
import com.example.ilmapp.config.PreferencesManager.saveSessionData
import com.example.ilmapp.config.PreferencesManager.saveUserData
import com.example.ilmapp.data.api.RetrofitInstance
import com.example.ilmapp.data.model.AuthViewModel
import com.example.ilmapp.data.model.AuthViewModelFactory
import com.example.ilmapp.data.model.RegisterRequest
import com.example.ilmapp.data.model.TokenManager
import com.example.ilmapp.databinding.FragmentRegisterBinding
import com.example.yourapp.views.PasswordEditText

class RegisterFragment : Fragment() {
    private lateinit var authViewModel: AuthViewModel
    private var _binding: FragmentRegisterBinding? = null
    private val roleViewModel: RoleViewModel by viewModels()
    private lateinit var tokenManager: TokenManager

    private val binding get() = _binding!!
    private var isPasswordVisible = false
    private var isPasswordAgainVisible = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        tokenManager = TokenManager(requireContext())
        val factory = AuthViewModelFactory(requireContext())
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val passwordUtils = PasswordEditText(requireContext(), null)

        val gotoLogin = binding.txtGoToLogin
        val btnRegister: ActionProcessButton = binding.btnSignIn
        btnRegister.setMode(ActionProcessButton.Mode.PROGRESS)
        val username = binding.edtRegiserUsername
        val email = binding.edtRegisterEmail
        val editTextPassword = binding.edtRegisterPassword
        val editTextPasswordAgain = binding.edtRegisterPasswordAgain

        gotoLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        passwordUtils.setPasswordVisibility(editTextPassword, isPasswordVisible)
        passwordUtils.setPasswordVisibility(editTextPasswordAgain, isPasswordAgainVisible)

        radioButton()

        btnRegister.setOnClickListener {
            RetrofitInstance.init(requireContext())

            if(validateInput(username, email, editTextPassword, editTextPasswordAgain)) {
                try {
                    val registerRequest = RegisterRequest(
                        userName = username.text.toString().trim(),
                        email = email.text.toString().trim(),
                        password = editTextPassword.text.toString().trim(),
                        matchingPassword = editTextPasswordAgain.text.toString().trim(),
                        role = roleViewModel.selectedRole.value.toString().trim()
                    )

                    loadingAnimation(btnRegister)
                    registerUser(registerRequest)
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }

    private fun registerUser(
        registerRequest: RegisterRequest
    ) {
        authViewModel.register(registerRequest)
        authViewModel.response.observe(viewLifecycleOwner) { response ->
            response.let {
                val accessToken = it!!
                val list = tokenManager.decodeJwtToken(accessToken)
                val name = list[0].name
                val role = list[0].roles
                saveUserData(requireContext(), name, registerRequest.email, role)
                saveSessionData(requireContext(), it, true)
            }
        }
        findNavController().navigate(R.id.action_registerFragment_home)
    }

    private fun validateInput(
        username: EditText,
        email: EditText,
        password: EditText,
        confirmPassword: EditText
    ): Boolean {
        if (roleViewModel.selectedRole.value.isNullOrEmpty()) {
            username.error = "Role is required"
            return false
        }
        if (username.text.isNullOrEmpty()) {
            username.error = "Username is required"
            return false
        }
        if (email.text.isNullOrEmpty()) {
            email.error = "Email is required"
            return false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
            email.error = "Invalid email format"
            return false
        }
        if (password.text.length < 6) {
            password.error = "Password must be at least 6 characters"
            return false
        }
        if (password.text.toString() != confirmPassword.text.toString()) {
            Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun loadingAnimation(btnRegister: ActionProcessButton) {
        btnRegister.setProgress(0)
        btnRegister.setProgress(50)
        btnRegister.setProgress(75)
        btnRegister.setProgress(100)
        binding.btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS)
        btnRegister.setProgress(1)
    }

    private fun radioButton() {
        binding.radioButtonRole.setOnCheckedChangeListener { _, checkedId ->
            roleViewModel.selectedRole.value = when (checkedId) {
                R.id.radio_teacher -> "teacher"
                R.id.radio_student -> "student"
                else -> "choose again"
            }
        }

        roleViewModel.selectedRole.observe(viewLifecycleOwner) { role ->
            Toast.makeText(requireContext(), "Selected Role: $role", Toast.LENGTH_SHORT).show()
        }
    }
}

class RoleViewModel : ViewModel() {
    val selectedRole = MutableLiveData<String>()
}
