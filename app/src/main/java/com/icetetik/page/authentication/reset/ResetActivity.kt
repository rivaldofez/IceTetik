package com.icetetik.page.authentication.reset

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.icetetik.R
import com.icetetik.databinding.ActivityResetBinding
import com.icetetik.util.Extension.showShortToast

class ResetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResetBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()

        binding.btnReset.setOnClickListener {
            val email = binding.edtEmail.text.toString()
            auth.sendPasswordResetEmail(email)
                .addOnSuccessListener {
                    showShortToast("SUccess")
                }
                .addOnFailureListener {
                    showShortToast("Failed")
                }

        }

    }

    private fun setButtonActions() {
        binding.apply {
            btnReset.setOnClickListener {

            }
        }
    }
}