package com.example.ilmapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.ilmapp.R
import com.example.ilmapp.data.model.AuthViewModel
import com.example.ilmapp.data.model.LoginRequest
import com.example.ilmapp.data.model.TokenManager
import com.example.ilmapp.databinding.FragmentLoginBinding
import com.example.yourapp.views.PasswordEditText

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var isPasswordVisible = false
    private lateinit var password: PasswordEditText
    private val authViewModel: AuthViewModel by viewModels()
    private lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gotoSignup = binding.txtGoToSignin
        val email = binding.edtLoginEmail
        val editTextPassword = binding.edtLoginPassword
        val btnLogin = binding.btnLogin
        password = PasswordEditText(requireContext(), null)

        gotoSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        password.setPasswordVisibility(editTextPassword, isPasswordVisible)

        btnLogin.setOnClickListener {
            val emailInput = email.text.toString().trim()
            val passwordInput = editTextPassword.text.toString().trim()

            if (emailInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(
                    requireContext(), "Email and password are required", Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            loginUser(emailInput, passwordInput)
        }
    }

    private fun loginUser(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        authViewModel.loginUser(loginRequest)
//        authViewModel.response.observe(viewLifecycleOwner) { response ->
//            response?.let {
//                val token = it.token
//                tokenManager.saveToken(token)
//                PreferencesManager.saveSessionData(requireContext(), tokenManager.getToken()!!, true)
//            }
//        }

        findNavController().navigate(R.id.action_loginFragment_home)

    }



//fun navigateBasedOnRole(context: Context, role: String?) {
//    when (role) {
//        "öğrenci" -> {
//            // Öğrenciye özel ekrana yönlendir
//            context.startActivity(Intent(context, StudentActivity::class.java))
//        }
//        "öğretmen" -> {
//            // Öğretmene özel ekrana yönlendir
//            context.startActivity(Intent(context, TeacherActivity::class.java))
//        }
//        else -> {
//            // Varsayılan olarak giriş ekranına yönlendir
//            context.startActivity(Intent(context, LoginActivity::class.java))
//        }
//    }
//}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
