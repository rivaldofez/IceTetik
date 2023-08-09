package com.icetetik.page.mainmood

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.icetetik.R
import com.icetetik.data.model.Mood
import com.icetetik.databinding.ActivityMoodBinding
import com.icetetik.page.infographic.InfographicActivity
import com.icetetik.page.journal.JournalActivity
import com.icetetik.page.questionnaire.QuestionnaireActivity
import com.icetetik.page.settings.SettingsActivity
import com.icetetik.page.statistics.StatisticsActivity
import com.icetetik.page.video.VideoActivity
import com.icetetik.relaxation.RelaxationActivity
import com.icetetik.util.Extension.animateChangeVisibility
import com.icetetik.util.Extension.showSnackBar
import com.icetetik.util.Helper
import com.icetetik.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class MoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoodBinding
    private var currentDate: LocalDate = LocalDate.now()
    private val viewModel: MoodViewModel by viewModels()
    private var userEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionItem()
        setObservers()
        loadUserSession()
    }

    private fun loadUserSession() {
        viewModel.getUserSession { email ->
            if (email != null) {
                userEmail = email
                loadTodayMood()
            }
        }
    }

    private fun loadTodayMood() {
        if (userEmail.isEmpty()) {
            binding.showSnackBar(getString(R.string.error_session_expired))
        } else {
            viewModel.getMood(userEmail, currentDate)
        }
    }

    private fun setObservers() {
        viewModel.mood.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    showLoading(isLoading = true)
                }

                is UiState.Failure -> {
                    showLoading(isLoading = false)
                    binding.showSnackBar(getString(R.string.error_when_load_mood_status))
                }

                is UiState.Success -> {
                    showLoading(isLoading = false)

                    val result = state.data
                    if (result == null) {
                        binding.showSnackBar(getString(R.string.error_today_mood_not_set_yet))
                    } else {
                        setTodayMoodView(state.data)
                    }
                }
            }
        }
    }

    private fun setTodayMoodView(mood: Mood) {
        binding.apply {
            tvMood.text = mood.condition
            ivMood.setImageResource(Helper.mapMoodConditionToDrawable(moodCondition = mood.condition))
        }

    }

    private fun setActionItem() {
        binding.apply {
            ivCalendar.setOnClickListener {
                startActivity(
                    Intent(this@MoodActivity, JournalActivity::class.java)
                )
            }

            ivQuiz.setOnClickListener {
                startActivity(
                    Intent(this@MoodActivity, QuestionnaireActivity::class.java)
                )
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

            lyRelaxation.setOnClickListener {
                startActivity(
                    Intent(this@MoodActivity, RelaxationActivity::class.java)
                )
            }

            llVideo.setOnClickListener {
                startActivity(
                    Intent(this@MoodActivity, VideoActivity::class.java)
                )
            }

            lyInformation.setOnClickListener {
                startActivity(
                    Intent(this@MoodActivity, InfographicActivity::class.java)
                )
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            sblLoading.root.animateChangeVisibility(isLoading)
            if (isLoading) sblLoading.lottieLoading.playAnimation() else sblLoading.lottieLoading.pauseAnimation()
        }
    }

    override fun onResume() {
        super.onResume()
        loadUserSession()
    }
}