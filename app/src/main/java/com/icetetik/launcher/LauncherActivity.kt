package com.icetetik.launcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.icetetik.MoodActivity
import com.icetetik.authentication.signup.SignUpActivity
import com.icetetik.databinding.ActivityLauncherBinding
import com.icetetik.journal.MoodChooserActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLauncherBinding
    private val viewModel: LauncherViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({


            viewModel.getUserSession { email ->
                if (email == null){
                    startActivity(Intent(this@LauncherActivity, SignUpActivity::class.java))
                } else {
                    startActivity(Intent(this@LauncherActivity, MoodChooserActivity::class.java))
                    finish()
                }
            }
        }, SPLASH_TIME )
    }

    companion object {
        private const val SPLASH_TIME: Long = 3000
    }
}