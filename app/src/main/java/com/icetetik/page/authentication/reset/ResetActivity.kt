package com.icetetik.page.authentication.reset

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.icetetik.R
import com.icetetik.databinding.ActivityResetBinding
import com.icetetik.databinding.SublayoutDialogConfirmationBinding
import com.icetetik.util.Extension.showShortToast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ResetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetBinding
    private val viewModel: ResetViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResetBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonActions()

    }

    private fun showResetConfirmationDialog(message: String, email: String) {
        val dialogBinding = SublayoutDialogConfirmationBinding.inflate(layoutInflater)

        val dialog = Dialog(this@ResetActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.apply {
            tvDialogMessage.text = message

            btnYes.setOnClickListener {
                viewModel.resetPasswordUser(email)
            }

            btnNo.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun setButtonActions() {
        binding.apply {
            btnReset.setOnClickListener {
                val email = edtEmail.text.toString()
                if(edtEmail.error.isNullOrEmpty() && email.isNotEmpty()){
                    showResetConfirmationDialog("Apakah kamu yakin untuk reset password?", email)
                } else {
                    showShortToast(getString(R.string.error_all_field_must_be_valid))
                }
            }
        }
    }
}