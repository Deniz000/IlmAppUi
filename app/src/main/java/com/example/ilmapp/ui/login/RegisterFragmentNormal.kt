//package com.example.ilmapp.ui.login
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.Toast
//import androidx.fragment.app.Fragment
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.navigation.fragment.findNavController
//import com.example.ilmapp.R
//import com.example.ilmapp.data.api.RetrofitClient
//import com.example.ilmapp.data.model.AuthViewModel
//import com.example.ilmapp.data.model.RegisterRequest
//import com.example.ilmapp.data.model.TokenManager
//import com.example.ilmapp.databinding.FragmentRegisterBinding
//import com.example.yourapp.views.PasswordEditText
//
//class RegisterFragment : Fragment() {
//    private val authViewModel: AuthViewModel by viewModels()
//    private var _binding: FragmentRegisterBinding? = null
//    private val roleViewModel: RoleViewModel by viewModels()
//    private lateinit var tokenManager: TokenManager
//
//    private val binding get() = _binding!!
//    private var isPasswordVisible = false
//    private var isPasswordAgainVisible = false
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        RetrofitClient.init(requireContext())
//
//        val passwordUtils = PasswordEditText(requireContext(), null)
//        val api = RetrofitClient.instance
//
//        val gotoLogin = binding.txtGoToLogin
//        val btnRegister = binding.btnRegister
//        val username = binding.edtRegiserUsername
//        val email = binding.edtRegisterEmail
//        val editTextPassword = binding.edtRegisterPassword
//        val editTextPasswordAgain = binding.edtRegisterPasswordAgain
//
//        gotoLogin.setOnClickListener {
//            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
//        }
//
//        passwordUtils.setPasswordVisibility(editTextPassword, isPasswordVisible)
//        passwordUtils.setPasswordVisibility(editTextPasswordAgain, isPasswordAgainVisible)
//
//        radioButton()
//
//        btnRegister.setOnClickListener {
//            try {
//                if (roleViewModel.selectedRole.value.isNullOrEmpty()) {
//                    username.error = "Role is required"
//                    return@setOnClickListener
//                }
//                if (username.text.isNullOrEmpty()) {
//                    username.error = "Username is required"
//                    return@setOnClickListener
//                }
//
//                if (email.text.isNullOrEmpty()) {
//                    email.error = "Email is required"
//                    return@setOnClickListener
//                }
//
//                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
//                    email.error = "Invalid email format"
//                    return@setOnClickListener
//                }
//
//                if (editTextPasswordAgain.text.length < 6) {
//                    editTextPasswordAgain.error = "Password must be at least 6 characters"
//                    return@setOnClickListener
//                }
//
//                if (editTextPassword.text.toString() != editTextPasswordAgain.text.toString()) {
//                    Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT)
//                        .show()
//                    return@setOnClickListener
//                }
//
//                val registerRequest = RegisterRequest(
//                    username = username.text.toString(),
//                    email = email.text.toString(),
//                    password = editTextPassword.text.toString(),
//                    passwordAgain = editTextPasswordAgain.text.toString(),
//                    role = roleViewModel.selectedRole.value.toString()
//                )
//
//                authViewModel.postData(registerRequest)
//            } catch (e: Exception) {
//                Toast.makeText(requireContext(), "Error: ${e.message}", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//       authViewModel.registerResponse.observe(viewLifecycleOwner) { response ->
//           response?.let {
//               tokenManager.saveToken(it.token)
//               Log.d("AuthInterceptor", "Token: $it.token")
//               Toast.makeText(requireContext(), "Registration Successful!", Toast.LENGTH_SHORT)
//                   .show()
//               findNavController().navigate(R.id.action_registerFragment_home)
//           }
//       }
//
//       authViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
//           Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
//       }
//    }
//    private fun radioButton() {
//        binding.radioButtonRole.setOnCheckedChangeListener { _, checkedId ->
//            roleViewModel.selectedRole.value = when (checkedId) {
//                R.id.radio_teacher -> "teacher"
//                R.id.radio_student -> "student"
//                else -> "choose again"
//            }
//        }
//
//        roleViewModel.selectedRole.observe(viewLifecycleOwner) { role ->
//            Toast.makeText(requireContext(), "Selected Role: $role", Toast.LENGTH_SHORT).show()
//        }
//    }
//}
//
//class RoleViewModel : ViewModel() {
//    val selectedRole = MutableLiveData<String>()
//}
