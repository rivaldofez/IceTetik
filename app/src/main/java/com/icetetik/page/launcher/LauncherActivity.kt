package com.icetetik.page.launcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.icetetik.page.mainmood.MoodActivity
import com.icetetik.page.authentication.AuthenticationActivity
import com.icetetik.databinding.ActivityLauncherBinding
import com.icetetik.relaxation.RelaxationActivity
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
//            viewModel.getUserSession { email ->
//                if (email == null){
//                    startActivity(Intent(this@LauncherActivity, AuthenticationActivity::class.java))
//                    finish()
//                } else {
//                    startActivity(Intent(this@LauncherActivity, MoodActivity::class.java))
//                    finish()
//                }
//            }
            startActivity(Intent(this@LauncherActivity, RelaxationActivity::class.java))
            finish()
        }, SPLASH_TIME )
    }

    companion object {
        private const val SPLASH_TIME: Long = 3000
    }
}