package com.icetetik.statistics

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.res.ResourcesCompat
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.icetetik.R
import com.icetetik.data.model.Mood
import com.icetetik.databinding.ActivityStatisticsBinding
import com.icetetik.util.Helper
import com.icetetik.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class StatisticsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatisticsBinding
    private val viewModel: StatisticsViewModel by viewModels()
    private var listMoodDataEntry: ArrayList<BarEntry> = ArrayList()

    private var currentLocalDate: LocalDate = LocalDate.now()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStatisticsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setButtonAction()
        setMonthView()

        viewModel.getUserSession { email ->
            if (email == null) {

            } else {
                viewModel.fetchMonthlyMood(userEmail = email, baseDate = LocalDate.now())
            }
        }

        setObserver()
    }


    private fun setObserver() {
        viewModel.monthlyMood.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }

                is UiState.Failure -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }

                is UiState.Success -> {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    setDataMonthly(state.data)

                }
            }
        }
    }

    private fun setButtonAction(){
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

    private fun setMonthView(){
        binding.tvMonth.text = Helper.monthYearFromDate(currentLocalDate)
    }

    private fun setDataMonthly(monthlyMood: List<Mood>) {
        var happy = 0
        var sad = 0
        var angry = 0
        var shock = 0
        var scared = 0
        var disgusting = 0

        monthlyMood.forEach { mood ->
            when (mood.condition) {
                "Senang" -> happy++
                "Sedih" -> sad++
                "Marah" -> angry++
                "Terkejut" -> shock++
                "Takut" -> scared++
                "Jijik" -> disgusting++
            }
        }

        Log.d("Teston", "Happy" + happy.toString())
        Log.d("Teston", "Sad" + sad.toString())
        Log.d("Teston", "Angry" + angry.toString())
        Log.d("Teston", "Shock" + shock.toString())
        Log.d("Teston", "Scared" + scared.toString())
        Log.d("Teston", "Disgusting" + disgusting.toString())

        listMoodDataEntry.clear()
        listMoodDataEntry.add(BarEntry(1f, happy.toFloat()))
        listMoodDataEntry.add(BarEntry(2f, sad.toFloat()))
        listMoodDataEntry.add(BarEntry(3f, angry.toFloat()))
        listMoodDataEntry.add(BarEntry(4f, shock.toFloat()))
        listMoodDataEntry.add(BarEntry(5f, scared.toFloat()))
        listMoodDataEntry.add(BarEntry(6f, disgusting.toFloat()))

        val moodBarDataset = BarDataSet(listMoodDataEntry, "Mood")
        moodBarDataset.setColor(getColor(R.color.cream_75))
        moodBarDataset.valueTextColor = Color.BLACK
        moodBarDataset.setDrawValues(false)

        val moodBarChart = binding.bcEmotions
        moodBarChart.refreshDrawableState()
        moodBarChart.setFitBars(false)
        moodBarChart.description.text = "" //set null to description
        moodBarChart.legend.isEnabled = false //disable legend

        //        barChart.getAxisRight().setDrawGridLines(false);
        moodBarChart.getAxisRight().isEnabled = false //disable right axis
        moodBarChart.xAxis.isEnabled = false
//        barChart.getAxisLeft().setDrawGridLines(false);
        moodBarChart.getXAxis().setDrawGridLines(false)

        moodBarChart.getAxisLeft().setDrawLabels(true) //disable label on right


        moodBarChart.getAxisLeft().typeface = ResourcesCompat.getFont(this, R.font.league_spartan_bold)
        moodBarChart.getAxisLeft().textSize = 16f
        moodBarChart.getAxisLeft().textColor = Color.WHITE

        moodBarChart.getAxisLeft().axisMinimum = 0F
        moodBarChart.setScaleEnabled(false) //disable zoom


        val moodBarData = BarData(moodBarDataset)
        binding.bcEmotions.notifyDataSetChanged()
        binding.bcEmotions.data = moodBarData
        binding.bcEmotions.invalidate()
    }
}