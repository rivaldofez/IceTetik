package com.icetetik.authentication.signin

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.icetetik.MoodActivity
import com.icetetik.R
import com.icetetik.databinding.ActivitySignInBinding
import com.icetetik.util.Extension.animateChangeVisibility
import com.icetetik.util.Extension.showSnackBar
import com.icetetik.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySignInBinding
    private val viewModel: SignInViewModel by viewModels()

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val signInGoogleLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResultSignInGoogle(task)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
        setButtonAction()
        setObservers()
    }

    private fun setButtonAction(){
        binding.apply {
            btnSignin.setOnClickListener {
                signInUser()
            }

            btnSigninGoogle.setOnClickListener {
                signInUserWithGoogle()
            }
        }
    }

    private fun setObservers(){
        viewModel.signInUser.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    showLoading(isLoading = true)
                }

                is UiState.Failure -> {
                    showLoading(isLoading = false)
                    binding.showSnackBar(state.error.toString())
                }

                is UiState.Success -> {
                    binding.showSnackBar("Login Succesfully")
                    val intent = Intent(this, MoodActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }

    private fun signInUser(){
        binding.apply {
            var isAllFieldValid = true

            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString()

            if (email.isEmpty() || !edtEmail.error.isNullOrEmpty())
                isAllFieldValid = false

            if (password.isEmpty() || !edtPassword.error.isNullOrEmpty())
                isAllFieldValid = false

            if (isAllFieldValid){
                viewModel.signIn(email = email, password = password)
            } else {
                showSnackBar("All field must be valid and filled, please try again")
            }
        }
    }

    private fun signInUserWithGoogle(){
        showLoading(isLoading = true)
        val signInIntent = googleSignInClient.signInIntent
        signInGoogleLauncher.launch(signInIntent)
    }

    private fun handleResultSignInGoogle(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account: GoogleSignInAccount? = task.result
            if (account == null ){
                showLoading(isLoading = false)
            } else {
                //update UI Account
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful){
                        val intent = Intent(this, MoodActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        binding.showSnackBar("Error occured while sign in your account, please try again")
                    }
                    showLoading(isLoading = false)
                }
            }
        } else {
            showLoading(isLoading = false)
            binding.showSnackBar("Error occured because system cannot retrieve user account data")
        }
    }

    private fun showLoading(isLoading: Boolean){
        binding.apply {
            edtEmail.isEnabled = !isLoading
            edtPassword.isEnabled = !isLoading
            sblLoading.root.animateChangeVisibility(isLoading)
        }
    }
}