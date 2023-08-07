package com.icetetik.page.statistics

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.icetetik.R
import com.icetetik.data.model.MoodCondition
import com.icetetik.databinding.ActivityStatisticsBinding
import com.icetetik.util.Extension.animateChangeVisibility
import com.icetetik.util.Extension.showSnackBar
import com.icetetik.util.Extension.toSp
import com.icetetik.util.Helper
import com.icetetik.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class StatisticsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatisticsBinding
    private val viewModel: StatisticsViewModel by viewModels()
    private var currentLocalDate: LocalDate = LocalDate.now()
    private var userEmail: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStatisticsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModel.getUserSession { email ->
            if (email == null) {
                binding.showSnackBar(getString(R.string.error_session_expired))
            } else {
                userEmail = email
                setMonthView()
                setButtonAction()
                setObserver()
                setupStyleBarChart()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            sblLoading.root.animateChangeVisibility(isLoading)
            btnNextMonth.isEnabled = !isLoading
            btnPrevMonth.isEnabled = !isLoading
            if (isLoading) sblLoading.lottieLoading.playAnimation() else sblLoading.lottieLoading.pauseAnimation()
        }
    }


    private fun setObserver() {
        viewModel.monthlyMood.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    showLoading(isLoading = true)
                }

                is UiState.Failure -> {
                    showLoading(isLoading = false)
                }

                is UiState.Success -> {
                    showLoading(isLoading = false)
                    setupBarChart(state.data)
                }
            }
        }
    }

    private fun setButtonAction() {
        binding.apply {
            btnNextMonth.setOnClickListener {
                currentLocalDate = currentLocalDate.plusMonths(1)
                setMonthView()
            }

            btnPrevMonth.setOnClickListener {
                currentLocalDate = currentLocalDate.minusMonths(1)
                setMonthView()
            }
        }
    }

    private fun setMonthView() {
        binding.tvMonth.text = Helper.monthYearFromDate(currentLocalDate)
        viewModel.fetchMonthlyMood(userEmail = userEmail, baseDate = currentLocalDate)
    }

    private fun setupStyleBarChart() {
        binding.bcEmotions.apply {
            setNoDataText(context.getString(R.string.error_no_data_this_month))
            setNoDataTextTypeface(
                ResourcesCompat.getFont(
                    this@StatisticsActivity,
                    R.font.league_spartan_bold
                )
            )
            setNoDataTextColor(Color.GRAY)
            getPaint(Chart.PAINT_INFO).textSize = 18f.toSp(this@StatisticsActivity)

            setFitBars(false)
            description.text = "" //set null to description
            legend.isEnabled = false //disable legend

            axisRight.isEnabled = false //disable right axis
            xAxis.isEnabled = false
            xAxis.setDrawGridLines(false)

            axisLeft.setDrawLabels(true) //disable label on right
            axisLeft.typeface =
                ResourcesCompat.getFont(this@StatisticsActivity, R.font.league_spartan_bold)
            axisLeft.textSize = 16f
            axisLeft.textColor = Color.WHITE
            axisLeft.axisMinimum = 0F
            setScaleEnabled(false) //disable zoom
        }
    }

    private fun setupBarChart(monthlyMoodStatistics: Map<MoodCondition, Int>) {
        if (monthlyMoodStatistics.isEmpty()) {
            binding.bcEmotions.clear()
            binding.bcEmotions.animate()
        } else {
            val listMoodDataEntry: ArrayList<BarEntry> = ArrayList()
            listMoodDataEntry.add(
                BarEntry(
                    1f,
                    (monthlyMoodStatistics[MoodCondition.HAPPY] ?: 0).toFloat()
                )
            )
            listMoodDataEntry.add(
                BarEntry(
                    2f,
                    (monthlyMoodStatistics[MoodCondition.SAD] ?: 0).toFloat()
                )
            )
            listMoodDataEntry.add(
                BarEntry(
                    3f,
                    (monthlyMoodStatistics[MoodCondition.ANGRY] ?: 0).toFloat()
                )
            )
            listMoodDataEntry.add(
                BarEntry(
                    4f,
                    (monthlyMoodStatistics[MoodCondition.SHOCK] ?: 0).toFloat()
                )
            )
            listMoodDataEntry.add(
                BarEntry(
                    5f,
                    (monthlyMoodStatistics[MoodCondition.SCARED] ?: 0).toFloat()
                )
            )
            listMoodDataEntry.add(
                BarEntry(
                    6f,
                    (monthlyMoodStatistics[MoodCondition.DISGUSTING] ?: 0).toFloat()
                )
            )

            val moodBarDataset = BarDataSet(listMoodDataEntry, getString(R.string.txt_mood))
            moodBarDataset.color = getColor(R.color.cream_75)
            moodBarDataset.valueTextColor = Color.BLACK
            moodBarDataset.setDrawValues(false)

            val moodBarChart = binding.bcEmotions
            moodBarChart.refreshDrawableState()

            val moodBarData = BarData(moodBarDataset)
            moodBarChart.notifyDataSetChanged()
            moodBarChart.data = moodBarData
            moodBarChart.invalidate()

            moodBarChart.animateXY(500,500)
        }
    }
}