package com.icetetik.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.icetetik.R
import com.icetetik.data.model.User
import com.icetetik.databinding.ActivitySettingsBinding
import com.icetetik.util.Extension.animateChangeVisibility
import com.icetetik.util.Extension.showSnackBar
import com.icetetik.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,  R.layout.activity_settings)

        viewModel.getUserSession { email ->
            if(email == null) {
                binding.showSnackBar("Session Expired")
            } else {
                viewModel.getUserInfo(email)
                setObservers()
            }
        }

        setButtonAction()
    }

    private fun setObservers() {
        viewModel.userInfo.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    showLoading(isLoading = true)
                }

                is UiState.Failure -> {
                    showLoading(isLoading = false)
                    binding.showSnackBar("There's error occured while process your request")
                }

                is UiState.Success -> {
                    showLoading(isLoading = false)

                    val result = state.data
                    if (result == null) {
                        binding.showSnackBar("User info not found")
                    } else {
                        updateCard(state.data)
                    }
                }
            }
        }
    }

    private fun updateCard(user: User) {
        binding.tvName.text = user.name
    }

    private fun setButtonAction(){
        binding.apply {
            rowPassword.root.setOnClickListener {

            }

            rowTheme.root.setOnClickListener {

            }

            rowLanguage.root.setOnClickListener {

            }

            rowDateTime.root.setOnClickListener {

            }

            rowHelp.root.setOnClickListener {

            }

            rowFaq.root.setOnClickListener {

            }

            btnLogout.setOnClickListener {

            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            sblLoading.root.animateChangeVisibility(isLoading)
        }
    }
}