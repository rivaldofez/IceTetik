package com.icetetik.authentication.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.icetetik.authentication.signin.SignInActivity
import com.icetetik.databinding.ActivitySignUpBinding
import com.icetetik.util.Extension.animateChangeVisibility
import com.icetetik.util.Extension.showSnackBar
import com.icetetik.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        setupButtonAction()
        setItemListener()
        setObservers()
    }

    private fun setObservers(){
        viewModel.signUpUser.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    showLoading(isLoading = true)
                }

                is UiState.Failure -> {
                    showLoading(isLoading = false)
                    binding.showSnackBar(state.error.toString())
                }

                is UiState.Success -> {
                    showLoading(isLoading = false)
                    binding.showSnackBar("Sign Up Succesfully")
                    val intent = Intent(this, SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun setItemListener(){
        binding.edtRetypePassword.addTextChangedListener {
            if(it.toString() != binding.edtPassword.text.toString()){
                binding.edtRetypePassword.error = "Password not match"
            }
        }
    }

    private fun setupButtonAction(){
        binding.btnSignup.setOnClickListener {
            signUpUser()
        }
    }

    private fun signUpUser(){
        binding.apply {
            var isAllFieldValid = true

            val fullname = edtFullname.text.toString().trim()
            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString()
            val retypePassword = edtRetypePassword.text.toString()

            if (fullname.isEmpty() || !edtFullname.error.isNullOrEmpty())
                isAllFieldValid = false

            if (email.isEmpty() || !edtEmail.error.isNullOrEmpty())
                isAllFieldValid = false

            if (password.isEmpty() || !edtPassword.error.isNullOrEmpty())
                isAllFieldValid = false

            if (retypePassword.isEmpty() || !edtRetypePassword.error.isNullOrEmpty())
                isAllFieldValid = false

            if (isAllFieldValid){
                viewModel.signUpUser(name = fullname, email = email, password = password)
            } else {
                showSnackBar("All field must be valid and filled, please try again")
            }
        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.apply {
            edtFullname.isEnabled = !isLoading
            edtEmail.isEnabled = !isLoading
            edtPassword.isEnabled = !isLoading
            edtRetypePassword.isEnabled = !isLoading
            sblLoading.root.animateChangeVisibility(isLoading)
        }
    }
}