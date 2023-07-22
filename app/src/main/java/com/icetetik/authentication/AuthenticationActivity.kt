package com.icetetik.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.icetetik.authentication.signin.SignInActivity
import com.icetetik.authentication.signup.SignUpActivity
import com.icetetik.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonAction()
    }

    private fun setButtonAction() {
        binding.apply {
            btnLogin.setOnClickListener {
                val intent = Intent(this@AuthenticationActivity, SignInActivity::class.java)
                startActivity(intent)
            }

            btnSignup.setOnClickListener {
                val intent = Intent(this@AuthenticationActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
        }
    }
}