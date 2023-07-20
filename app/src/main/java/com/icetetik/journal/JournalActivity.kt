package com.icetetik.journal

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.icetetik.MoodActivity
import com.icetetik.authentication.signup.SignUpActivity
import com.icetetik.data.model.Mood
import com.icetetik.data.model.MoodItemView
import com.icetetik.databinding.ActivityJournalBinding
import com.icetetik.util.KeyParcelable
import com.icetetik.util.UiState
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

    private var userEmail: String = ""

    private val getResultChosenMood = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val moodItemView = it.data?.getParcelableExtra<MoodItemView>(KeyParcelable.MOOD_CONDITION)
            Log.d("Teston", moodItemView.toString())
        }
    }

    private val getResultNoteMood = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val note = it.data?.getStringExtra(KeyParcelable.MOOD_NOTE)
            Log.d("Teston", note.toString())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setMonthView()

        viewModel.getUserSession { email ->
            if (email == null) {

            } else {
                userEmail = email
            }
        }

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

            btnAddMood.setOnClickListener {
                val intent = Intent(this@JournalActivity, MoodChooserActivity::class.java)
                getResultChosenMood.launch(intent)
            }

            btnAddNote.setOnClickListener {
                val intent = Intent(this@JournalActivity, MoodNoteWriterActivity::class.java)
                getResultNoteMood.launch(intent)
            }
        }

//        binding.btnEditMoodDum.setOnClickListener {
//            val intent = Intent(this@JournalActivity, MoodChooserActivity::class.java)
//            getResultChosenMood.launch(intent)
//            Log.d("Teston", "called")
//        }


//        binding.btnAddMoodDum.setOnClickListener {
//           if(userEmail.isEmpty()){
//               Toast.makeText( this, "Empty Email Session", Toast.LENGTH_SHORT).show()
//           } else {
//
//               val mood = Mood(posted = "${selectedDate.year}-${selectedDate.monthValue}-${selectedDate.dayOfMonth}\"", condition = "Happy", note = "Saya baru mendapat uang kaget")
//
//               viewModel.addMood(
//                   userEmail = userEmail,
//                   mood = mood,
//                   uploadDate = selectedDate
//               )
//           }
//        }

//        binding.btnLoadMoodDum.setOnClickListener {
//            if(userEmail.isEmpty()){
//                Toast.makeText( this, "Empty Email Session", Toast.LENGTH_SHORT).show()
//            } else {
//                viewModel.getMood(userEmail, selectedDate)
//            }
//        }

        viewModel.mood.observe(this) { state ->
            when (state) {
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

        viewModel.addMood.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }

                is UiState.Failure -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }

                is UiState.Success -> {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                }
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

        val startWhitespaceElement = MutableList(dayOfWeek % 7) { "" }
        val calendarElement = ArrayList<String>()
        (1..daysInMonth).mapTo(calendarElement) { it.toString() }

        val endCount = (7 - ((startWhitespaceElement.size + calendarElement.size) % 7)) % 7
        val endWhiteSpace = MutableList(endCount) { "" }

        daysInMonthArray.addAll(startWhitespaceElement)
        daysInMonthArray.addAll(calendarElement)
        daysInMonthArray.addAll(endWhiteSpace)

        return daysInMonthArray
    }

    private fun monthYearFromDate(date: LocalDate): String {
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