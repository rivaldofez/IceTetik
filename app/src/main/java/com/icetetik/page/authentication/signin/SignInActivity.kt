package com.icetetik.page.authentication.signin

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
import com.icetetik.page.mainmood.MoodActivity
import com.icetetik.R
import com.icetetik.data.model.User
import com.icetetik.databinding.ActivitySignInBinding
import com.icetetik.util.Extension.animateChangeVisibility
import com.icetetik.util.Extension.showShortToast
import com.icetetik.util.Extension.showSnackBar
import com.icetetik.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val viewModel: SignInViewModel by viewModels()

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private var googleIdToken: String? = null
    private var googleUser: User? = null

    private val signInGoogleLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
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

    private fun setButtonAction() {
        binding.apply {
            btnSignin.setOnClickListener {
                signInUser()
            }

            btnSigninGoogle.setOnClickListener {
                signInUserWithGoogle()
            }
        }
    }

    private fun setObservers() {
        viewModel.saveUserInfo.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    showLoading(isLoading = true)
                }

                is UiState.Failure -> {
                    showLoading(isLoading = false)
                    binding.showSnackBar("Error occured because system cannot save account data")
                }

                is UiState.Success -> {
                    signInWithCredentialGoogle()
                }
            }
        }


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
                    showShortToast("Login Successfully")
                    navigateToMainView()
                }
            }
        }

        viewModel.userInfo.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    showLoading(isLoading = true)
                }

                is UiState.Failure -> {
                    showLoading(isLoading = false)
                }

                is UiState.Success -> {
                    showLoading(isLoading = false)

                    val result = state.data
                    if (result == null) {
                        saveUserInfo()
                    } else {
                        signInWithCredentialGoogle()
                    }
                }
            }
        }
    }


    private fun signInUser() {
        binding.apply {
            var isAllFieldValid = true

            val email = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString()

            if (email.isEmpty() || !edtEmail.error.isNullOrEmpty())
                isAllFieldValid = false

            if (password.isEmpty() || !edtPassword.error.isNullOrEmpty())
                isAllFieldValid = false

            if (isAllFieldValid) {
                viewModel.signIn(email = email, password = password)
            } else {
                showSnackBar("All field must be valid and filled, please try again")
            }
        }
    }

    private fun saveUserInfo() {
        if (googleUser == null) {
            binding.showSnackBar("Error occured because system cannot retrieve account data")
        } else {
            viewModel.saveUserInfo(googleUser!!)
        }
    }

    private fun signInWithCredentialGoogle() {
        //update UI Account
        showLoading(isLoading = true)
        val credential = GoogleAuthProvider.getCredential(googleIdToken, null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                showShortToast("Login Successfully")
                navigateToMainView()
            } else {
                binding.showSnackBar("Error occured while sign in your account, please try again")
            }
            showLoading(isLoading = false)
        }
    }

    private fun signInUserWithGoogle() {
        showLoading(isLoading = true)
        val signInIntent = googleSignInClient.signInIntent
        signInGoogleLauncher.launch(signInIntent)
    }

    private fun navigateToMainView() {
        val intent = Intent(this@SignInActivity, MoodActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleResultSignInGoogle(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account: GoogleSignInAccount? = task.result
            if (account == null) {
                showLoading(isLoading = false)
                binding.showSnackBar("Error occured because system cannot retrieve account data")
            } else {
                val email = account.email
                val name = account.displayName
                googleIdToken = account.idToken

                if (!email.isNullOrEmpty() && !name.isNullOrEmpty()) {
                    googleUser = User(email = email, name = name)
                    loadUserInfo()
                } else {
                    binding.showSnackBar("Error occured because system cannot retrieve account data")
                }
            }
        } else {
            showLoading(isLoading = false)
            binding.showSnackBar("Error occured because system cannot retrieve account data")
        }
    }

    private fun loadUserInfo() {
        if (googleUser == null) {
            binding.showSnackBar("Error occured because system cannot retrieve account data")
        } else {
            val email = googleUser!!.email
            if (email.isEmpty()) {
                binding.showSnackBar("Error occured because system cannot retrieve account data")
            } else {
                viewModel.getUserInfo(email)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            edtEmail.isEnabled = !isLoading
            edtPassword.isEnabled = !isLoading
            sblLoading.root.animateChangeVisibility(isLoading)

            if (isLoading) sblLoading.lottieLoading.playAnimation() else sblLoading.lottieLoading.pauseAnimation()
        }
    }


}