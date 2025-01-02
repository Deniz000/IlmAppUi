package com.example.ilmapp.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dd.processbutton.iml.ActionProcessButton
import com.example.ilmapp.R
import com.example.ilmapp.config.PreferencesManager
import com.example.ilmapp.data.model.AuthViewModel
import com.example.ilmapp.data.model.AuthViewModelFactory
import com.example.ilmapp.data.model.LoginRequest
import com.example.ilmapp.data.model.TokenManager
import com.example.ilmapp.databinding.FragmentLoginBinding
import com.example.yourapp.views.PasswordEditText

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var isPasswordVisible = false
    private lateinit var password: PasswordEditText
    private lateinit var tokenManager: TokenManager
    private lateinit var authViewModel: AuthViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        tokenManager = TokenManager(requireContext())
        val factory = AuthViewModelFactory(requireContext())
        authViewModel = ViewModelProvider(this, factory)[AuthViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gotoSignup = binding.txtGoToSignin
        val email = binding.edtLoginEmail
        val editTextPassword = binding.edtLoginPassword
        password = PasswordEditText(requireContext(), null)

        val btLogin: ActionProcessButton = binding.btnLogin
        btLogin.setMode(ActionProcessButton.Mode.PROGRESS)
        gotoSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        password.setPasswordVisibility(editTextPassword, isPasswordVisible)

        btLogin.setOnClickListener {
            val emailInput = email.text.toString().trim()
            val passwordInput = editTextPassword.text.toString().trim()

            if (emailInput.isEmpty() || passwordInput.isEmpty()) {
                Toast.makeText(
                    requireContext(), "Email and password are required", Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            loadingAnimation(btLogin)
            loginUser(emailInput, passwordInput)

        }
    }

    private fun loadingAnimation(btnLogin: ActionProcessButton) {
        btnLogin.setProgress(0)
        btnLogin.setProgress(50)
        btnLogin.setProgress(75)
        btnLogin.setProgress(100)
        binding.btnLogin.setMode(ActionProcessButton.Mode.ENDLESS)
        btnLogin.setProgress(1)
    }

    private fun loginUser(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        authViewModel.login(loginRequest)
        val access: String? = tokenManager.getToken()

        val list = tokenManager.decodeJwtToken(access.toString())
        val name = list[0].name
        val role = list[0].roles
        PreferencesManager.saveUserData(
            requireContext(),
            name,
            email,
            role
        )
        PreferencesManager.saveSessionData(requireContext(), access.toString(), true)

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
