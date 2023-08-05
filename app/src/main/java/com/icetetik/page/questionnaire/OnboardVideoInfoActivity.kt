package com.icetetik.page.questionnaire

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.icetetik.databinding.ActivityOnboardVideoInfoBinding
import com.icetetik.page.infographic.InfographicActivity
import com.icetetik.page.mainmood.MoodActivity
import com.icetetik.page.video.VideoActivity

class OnboardVideoInfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardVideoInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnboardVideoInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonActions()
    }

    private fun setButtonActions() {
        binding.apply {
            btnHome.setOnClickListener {
                startActivity(
                    Intent(this@OnboardVideoInfoActivity, MoodActivity::class.java)
                )
                finishAffinity()
            }

            btnVideo.setOnClickListener {
                startActivity(
                    Intent(this@OnboardVideoInfoActivity, VideoActivity::class.java)
                )
            }

            btnInfographic.setOnClickListener {
                startActivity(
                    Intent(this@OnboardVideoInfoActivity, InfographicActivity::class.java)
                )
            }
        }
    }
}