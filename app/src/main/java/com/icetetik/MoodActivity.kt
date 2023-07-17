package com.icetetik

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.icetetik.databinding.ActivityMoodBinding
import com.icetetik.journal.JournalActivity
import com.icetetik.settings.SettingsActivity
import com.icetetik.statistics.StatisticsActivity

class MoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoodBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionItem()
    }

    private fun setActionItem() {
        binding.apply {
            ivCalendar.setOnClickListener {
                startActivity(
                    Intent(this@MoodActivity, JournalActivity::class.java)
                )
            }

            ivQuiz.setOnClickListener {

            }

            ivSettings.setOnClickListener {
                startActivity(
                    Intent(this@MoodActivity, SettingsActivity::class.java)
                )
            }

            ivStatistics.setOnClickListener {
                startActivity(
                    Intent(this@MoodActivity, StatisticsActivity::class.java)
                )
            }


        }
    }
}