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
import com.icetetik.util.Extension.animateChangeVisibility
import com.icetetik.util.Extension.showLongToast
import com.icetetik.util.Extension.showShortToast
import com.icetetik.util.Extension.showSnackBar
import com.icetetik.util.UiState
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
        setObservers()

    }

    private fun setObservers() {
        viewModel.resetPasswordUser.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    showLoading(isLoading = true)
                }

                is UiState.Failure -> {
                    showLoading(isLoading = false)
                    binding.showSnackBar(getString(R.string.error_cannot_save_account_data))
                }

                is UiState.Success -> {
                    showLoading(isLoading = false)
                    showLongToast("Success request reset password, please check your email to setup new password")
                    finish()
                }
            }
        }
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
                dialog.dismiss()
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

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            edtEmail.isEnabled = !isLoading
            sblLoading.root.animateChangeVisibility(isLoading)

            if (isLoading) sblLoading.lottieLoading.playAnimation() else sblLoading.lottieLoading.pauseAnimation()
        }
    }
}