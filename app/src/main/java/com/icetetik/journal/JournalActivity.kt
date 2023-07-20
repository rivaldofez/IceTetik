package com.icetetik.journal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.icetetik.databinding.ActivityJournalBinding
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@AndroidEntryPoint
class JournalActivity : AppCompatActivity(), CalendarItemCallback {
    private lateinit var binding: ActivityJournalBinding
    private val viewModel: JournalViewModel by viewModels()


    private var currentAdapterDate: LocalDate = LocalDate.now()
    private var selectedDate: LocalDate = LocalDate.now()
    private val adapter = CalendarAdapter(this@JournalActivity, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setMonthView()

        val layoutManager = GridLayoutManager(this@JournalActivity, 7)

        binding.rvCalendar.layoutManager = layoutManager
        binding.rvCalendar.adapter = adapter


        binding.apply {
            btnNextMonth.setOnClickListener {
                currentAdapterDate = currentAdapterDate.plusMonths(1)
                setMonthView()
            }

            btnPrevMonth.setOnClickListener {
                currentAdapterDate = currentAdapterDate.minusMonths(1)
                setMonthView()
            }
        }



    }

    private fun setMonthView() {
        binding.tvMonth.text = monthYearFromDate(currentAdapterDate)
        val daysInMonth = generateDaysInMonthArray(currentAdapterDate)
        adapter.setData(daysInMonth, currentAdapterDate, selectedDate)
    }

    private fun generateDaysInMonthArray(date: LocalDate): ArrayList<String> {
        val daysInMonthArray = ArrayList<String>()

        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()

        val firstOfMonth = currentAdapterDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value

        val startWhitespaceElement = MutableList(dayOfWeek % 7) {""}
        val calendarElement = ArrayList<String>()
        (1..daysInMonth).mapTo(calendarElement) { it.toString() }

        val endCount = (7 - ((startWhitespaceElement.size + calendarElement.size) % 7)) % 7
        val endWhiteSpace = MutableList(endCount) {""}

        daysInMonthArray.addAll(startWhitespaceElement)
        daysInMonthArray.addAll(calendarElement)
        daysInMonthArray.addAll(endWhiteSpace)

        return daysInMonthArray
    }

    private fun monthYearFromDate(date: LocalDate): String{
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return formatter.format(date)
    }

    override fun onItemCalendarClicked(date: String) {
        Log.d("Teston", "date " + date)
        selectedDate = LocalDate.of(
            currentAdapterDate.year,
            currentAdapterDate.month,
            date.toInt()
        )

        setMonthView()
    }

}