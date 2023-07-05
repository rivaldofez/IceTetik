package com.icetetik.launcher

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.icetetik.MainActivity
import com.icetetik.databinding.ActivityLauncherBinding
import com.icetetik.journal.JournalActivity

class LauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLauncherBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@LauncherActivity, JournalActivity::class.java))
            finish()
        }, SPLASH_TIME )
    }

    companion object {
        private const val SPLASH_TIME: Long = 3000
    }
}