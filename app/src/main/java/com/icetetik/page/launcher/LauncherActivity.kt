package com.icetetik.page.launcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.icetetik.page.mainmood.MoodActivity
import com.icetetik.page.authentication.AuthenticationActivity
import com.icetetik.databinding.ActivityLauncherBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLauncherBinding
    private val viewModel: LauncherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkTheme()
    }

    private fun checkTheme(){
        lifecycleScope.launchWhenCreated {
            launch {
                viewModel.getThemeSetting().collect { theme ->
                    if (theme.isNullOrEmpty()) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        viewModel.saveThemeSetting(themeId = 0)
                    } else {
                        when(theme.toInt()){
                            0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                            1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                            else ->  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        }
                    }

                    delay(SPLASH_TIME)
                    viewModel.getUserSession { email ->
                        if (email == null){
                            startActivity(Intent(this@LauncherActivity, AuthenticationActivity::class.java))
                            finish()
                        } else {
                            startActivity(Intent(this@LauncherActivity, MoodActivity::class.java))
                            finish()
                        }
                    }
                }
            }

        }
    }

    companion object {
        private const val SPLASH_TIME: Long = 3000
    }
}