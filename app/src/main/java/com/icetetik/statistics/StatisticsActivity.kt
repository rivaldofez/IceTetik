package com.icetetik.statistics

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.icetetik.R
import com.icetetik.databinding.ActivityStatisticsBinding
import com.icetetik.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

@AndroidEntryPoint
class StatisticsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatisticsBinding
    private val viewModel: StatisticsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStatisticsBinding.inflate(layoutInflater)

        setContentView(binding.root)


        val barChart = binding.bcEmotions


        val list: ArrayList<BarEntry> = ArrayList()

        list.add(BarEntry(100f,100f))
        list.add(BarEntry(101f,200f))
        list.add(BarEntry(102f,300f))
        list.add(BarEntry(103f,400f))
        list.add(BarEntry(104f,500f))
        list.add(BarEntry(105f,600f))

        val barDataSet= BarDataSet(list,"List")

        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS,255)
        barDataSet.valueTextColor= Color.BLACK
        barDataSet.setDrawValues(false)

        val barData= BarData(barDataSet)

        barChart.setFitBars(false)

        barChart.data= barData

        barChart.description.text= "" //set null to description
        barChart.legend.isEnabled = false //disable legend

        //        barChart.getAxisRight().setDrawGridLines(false);
        barChart.getAxisRight().isEnabled = false //disable right axis
        barChart.xAxis.isEnabled = false
//        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false)

        barChart.getAxisLeft().setDrawLabels(false) //disable label on right
        barChart.getAxisLeft().axisMinimum = 0F
        barChart.setScaleEnabled(false) //disable zoom


        viewModel.getUserSession { email ->
            if (email == null) {

            } else {
                viewModel.fetchMonthlyMood(userEmail = email, baseDate = LocalDate.now())
            }
        }

        setObserver()
    }


    private fun setObserver(){
        viewModel.monthlyMood.observe(this) { state ->
            when(state) {
                is UiState.Loading -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }

                is UiState.Failure -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }

                is UiState.Success -> {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    Log.d("Teston", state.data.toString())

                }
            }
        }
    }
}