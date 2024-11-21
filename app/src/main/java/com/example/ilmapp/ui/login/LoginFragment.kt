package com.example.ilmapp.ui.login

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.ilmapp.databinding.FragmentLoginBinding

import com.example.ilmapp.R
import com.example.yourapp.views.PasswordEditText

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private var isPasswordVisible = false
    private lateinit var password: PasswordEditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gotoSigin = binding.txtGoToSignin
        val editTextPassword = binding.edtLoginPassword
        password = PasswordEditText(requireContext(), null)


        gotoSigin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        password.setPasswordVisibility(editTextPassword, isPasswordVisible)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}