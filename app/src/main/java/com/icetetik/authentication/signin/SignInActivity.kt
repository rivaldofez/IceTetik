package com.icetetik.authentication.signin

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.icetetik.MoodActivity
import com.icetetik.R
import com.icetetik.data.model.User
import com.icetetik.databinding.ActivitySignInBinding
import com.icetetik.databinding.SublayoutDialogConfirmationBinding
import com.icetetik.databinding.SublayoutDialogSetPasswordBinding
import com.icetetik.util.Extension.animateChangeVisibility
import com.icetetik.util.Extension.showSnackBar
import com.icetetik.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.sign

@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySignInBinding
    private val viewModel: SignInViewModel by viewModels()

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private var googleIdToken: String? = null
    private var googleUser: User? = null

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
        viewModel.saveUserInfo.observe(this) { state ->
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
                    binding.showSnackBar("Login Succesfully")
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
                        //new user
                        showSetPasswordDialog()
                    } else {
                        signInWithCredentialGoogle()
                    }
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

    private fun signInWithCredentialGoogle(){
        //update UI Account
                showLoading(isLoading = true)
                val credential = GoogleAuthProvider.getCredential(googleIdToken, null)
                firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
                    if (it.isSuccessful){
                        navigateToMainView()
                    } else {
                        binding.showSnackBar("Error occured while sign in your account, please try again")
                    }
                    showLoading(isLoading = false)
                }
    }

    private fun signInUserWithGoogle(){
        showLoading(isLoading = true)
        val signInIntent = googleSignInClient.signInIntent
        signInGoogleLauncher.launch(signInIntent)
    }

    private fun navigateToMainView(){
        val intent = Intent(this@SignInActivity, MoodActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun handleResultSignInGoogle(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful){
            val account: GoogleSignInAccount? = task.result
            if (account == null ){
                showLoading(isLoading = false)
            } else {
                val email = account.email
                val name = account.displayName
                googleIdToken = account.idToken

                if(!email.isNullOrEmpty() && !name.isNullOrEmpty()){
                    googleUser = User(email = email, name = name)
                    loadUserInfo()
                } else {
                    //handle null user account
                }

//                //update UI Account
//                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
//                firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
//                    if (it.isSuccessful){
//                        //success login
//                    } else {
//                        binding.showSnackBar("Error occured while sign in your account, please try again")
//                    }
//                    showLoading(isLoading = false)
//                }
            }
        } else {
            showLoading(isLoading = false)
            binding.showSnackBar("Error occured because system cannot retrieve user account data")
        }
    }

    private fun loadUserInfo(){
        if (googleUser == null){

        } else {
            val email = googleUser!!.email
            if (email.isEmpty()){
            } else {
                viewModel.getUserInfo(email)
            }
        }
    }

    private fun showSetPasswordDialog(){
        val dialogBinding = SublayoutDialogSetPasswordBinding.inflate(layoutInflater)

        val dialog = Dialog(this@SignInActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.apply {
            edtRetypePassword.addTextChangedListener {
                if(it.toString() != edtPassword.text.toString()){
                    edtRetypePassword.error = "Password not match"
                }
            }


            btnYes.setOnClickListener {
                var isAllFieldValid = true
                val password = edtPassword.text.toString()
                val retypePassword = edtRetypePassword.text.toString()

                if (password.isEmpty() || !edtPassword.error.isNullOrEmpty())
                    isAllFieldValid = false

                if (retypePassword.isEmpty() || !edtRetypePassword.error.isNullOrEmpty())
                    isAllFieldValid = false

                if (isAllFieldValid){
                    googleUser?.password = password
                    if (googleUser == null){
                        //handle null
                    } else {
                        viewModel.saveUserInfo(googleUser!!)
                    }
                } else {
                    Toast.makeText(this@SignInActivity,"All field must be valid and filled, please try again" ,Toast.LENGTH_SHORT).show()
                }
            }

            btnNo.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun showLoading(isLoading: Boolean){
        binding.apply {
            edtEmail.isEnabled = !isLoading
            edtPassword.isEnabled = !isLoading
            sblLoading.root.animateChangeVisibility(isLoading)
        }
    }


}