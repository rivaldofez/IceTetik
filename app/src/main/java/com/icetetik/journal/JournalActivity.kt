package com.icetetik.journal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.icetetik.R
import com.icetetik.databinding.ActivityJournalBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Date

class JournalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJournalBinding
    private var selectedDate: LocalDate = LocalDate.now()
    private val adapter = CalendarAdapter(this@JournalActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setMonthView()

        val layoutManager = GridLayoutManager(this@JournalActivity, 7)

        binding.rvCalendar.layoutManager = layoutManager
        binding.rvCalendar.adapter = adapter

    }

    private fun setMonthView() {
        binding.tvMonth.text = monthYearFromDate(selectedDate)
        val daysInMonth = generateDaysInMonthArray(selectedDate)
        adapter.setData(daysInMonth)
    }

    private fun generateDaysInMonthArray(date: LocalDate): ArrayList<String> {
        val daysInMonthArray = ArrayList<String>()

        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()

        val firstOfMonth = selectedDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value

        for(i in 1..42){
            if( i <= dayOfWeek || i > daysInMonth + dayOfWeek){
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return daysInMonthArray
    }

    private fun monthYearFromDate(date: LocalDate): String{
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return formatter.format(date)
    }

}