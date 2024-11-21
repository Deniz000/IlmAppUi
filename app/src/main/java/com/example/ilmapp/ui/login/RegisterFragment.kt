package com.example.ilmapp.ui.login

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.ilmapp.databinding.FragmentRegisterBinding

import com.example.ilmapp.R
import com.example.yourapp.views.PasswordEditText

class RegisterFragment : Fragment() {

private var _binding: FragmentRegisterBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var isPasswordVisible = false
    private var isPasswordAgainVisible = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

      _binding = FragmentRegisterBinding.inflate(inflater, container, false)
      return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val passwordUtils = PasswordEditText(requireContext(), null)
        val btnRegister = binding.btnRegister
        val gotoLogin = binding.txtGoToLogin
        val editTextPassword = binding.edtRegisterPassword
        val editTextPasswordAgain = binding.edtRegisterPasswordAgain

        btnRegister.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_home)
        }

        gotoLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }
        passwordUtils.setPasswordVisibility(editTextPassword, isPasswordVisible)
        passwordUtils.setPasswordVisibility(editTextPasswordAgain, isPasswordAgainVisible)

    }}