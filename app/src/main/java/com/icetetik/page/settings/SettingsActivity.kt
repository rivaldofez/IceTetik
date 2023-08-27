package com.icetetik.page.settings

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.icetetik.R
import com.icetetik.page.authentication.AuthenticationActivity
import com.icetetik.data.model.User
import com.icetetik.databinding.ActivitySettingsBinding
import com.icetetik.databinding.SublayoutDialogConfirmationBinding
import com.icetetik.databinding.SublayoutDialogDeleteAccountBinding
import com.icetetik.databinding.SublayoutDialogThemeBinding
import com.icetetik.page.authentication.reset.ResetActivity
import com.icetetik.page.settings.faq.FaqActivity
import com.icetetik.util.Extension.animateChangeVisibility
import com.icetetik.util.Extension.showLongToast
import com.icetetik.util.Extension.showSnackBar
import com.icetetik.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: SettingsViewModel by viewModels()
    private var userEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_settings)

        viewModel.getUserSession { email ->
            if (email == null) {
                binding.showSnackBar(getString(R.string.error_session_expired))
            } else {
                userEmail = email
                loadUserInfo()
                setObservers()
            }
        }
        setButtonAction()
    }

    private fun loadUserInfo() {
        if (userEmail.isEmpty()) {
            binding.showSnackBar(getString(R.string.error_session_expired))
        } else {
            viewModel.getUserInfo(userEmail)
        }
    }

    private fun setObservers() {
        viewModel.signOut.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    showLoading(isLoading = true)
                }

                is UiState.Failure -> {
                    showLoading(isLoading = false)
                    binding.showSnackBar(getString(R.string.error_process_request))
                }

                is UiState.Success -> {
                    showLoading(isLoading = false)
                    val intent = Intent(this@SettingsActivity, AuthenticationActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
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
                    binding.showSnackBar(getString(R.string.error_process_request))
                }

                is UiState.Success -> {
                    showLoading(isLoading = false)

                    val result = state.data
                    if (result == null) {
                        binding.showSnackBar(getString(R.string.error_user_info_not_found))
                    } else {
                        updateCard(state.data)
                    }
                }
            }
        }

        viewModel.deleteAccount.observe(this) { state ->
            when(state) {
                is UiState.Loading -> {
                    showLoading(isLoading = true)
                }

                is UiState.Failure -> {
                    showLoading(isLoading = false)
                    showLongToast(getString(R.string.error_reauthenticate_delete_account))
                }

                is UiState.Success -> {
                    showLoading(isLoading = false)
                    showLongToast(state.data)
                    val intent = Intent(this@SettingsActivity, AuthenticationActivity::class.java)
                    startActivity(intent)
                    finishAffinity()
                }
            }
        }
    }

    private fun updateCard(user: User) {
        binding.tvName.text = user.name
    }

    private fun setButtonAction() {
        binding.apply {
            rowPassword.root.setOnClickListener {
                startActivity(
                    Intent(this@SettingsActivity, ResetActivity::class.java)
                )
            }

            rowDeleteAccount.root.setOnClickListener {
                showDeleteAccountDialog(getString(R.string.msg_delete_account_cannot_undone))
            }

            rowTheme.root.setOnClickListener {
                showThemeDialog(getString(R.string.txt_select_theme))
            }

            rowFeedback.root.setOnClickListener {
                val intent = Intent(this@SettingsActivity, FeedbackActivity::class.java)
                startActivity(intent)
            }

            rowHelp.root.setOnClickListener {
                val intent = Intent(this@SettingsActivity, HelpActivity::class.java)
                startActivity(intent)
            }

            rowFaq.root.setOnClickListener {
                val intent = Intent(this@SettingsActivity, FaqActivity::class.java)
                startActivity(intent)
            }

            btnLogout.setOnClickListener {
                showConfirmationDialog(getString(R.string.msg_sign_out_dialog))
            }

            btnSync.setOnClickListener {
                loadUserInfo()
            }
        }
    }

    private fun showDeleteAccountDialog(message: String) {
        val dialogBinding = SublayoutDialogDeleteAccountBinding.inflate(layoutInflater)

        val dialog = Dialog(this@SettingsActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.apply {
            tvDialogMessage.text = message

            btnDelete.setOnClickListener {
                viewModel.deleteAccountUser()
            }

            btnCancel.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun showConfirmationDialog(message: String) {
        val dialogBinding = SublayoutDialogConfirmationBinding.inflate(layoutInflater)

        val dialog = Dialog(this@SettingsActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.apply {
            tvDialogMessage.text = message

            btnYes.setOnClickListener {
                viewModel.signOutUser()
            }

            btnNo.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun showThemeDialog(message: String) {
        val dialogBinding = SublayoutDialogThemeBinding.inflate(layoutInflater)

        val dialog = Dialog(this@SettingsActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        dialogBinding.apply {
            tvDialogMessage.text = message

            val themeTitles = resources.getStringArray(R.array.themes)
            val arrayAdapter = ArrayAdapter(
                this@SettingsActivity, R.layout.item_spinner_theme,
                themeTitles
            )

            dialogBinding.spnTheme.adapter = arrayAdapter
            dialogBinding.spnTheme.isSelected = false
            var selectedTheme: Int? = null

            lifecycleScope.launch {
                viewModel.getThemeSetting().collect { theme ->
                    if (!theme.isNullOrEmpty()) {
                        selectedTheme = theme.toInt()
                        selectedTheme?.let {
                            dialogBinding.spnTheme.setSelection(it)
                        }
                    }
                }
            }

            dialogBinding.spnTheme.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        selectedTheme = position
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {}
                }


            btnClose.setOnClickListener {
                selectedTheme?.let {
                    when (it) {
                        0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    }
                    viewModel.saveThemeSetting(themeId = it)
                    dialog.dismiss()
                }
            }
        }
        dialog.show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            sblLoading.root.animateChangeVisibility(isLoading)
            if (isLoading) sblLoading.lottieLoading.playAnimation() else sblLoading.lottieLoading.pauseAnimation()
        }
    }
}