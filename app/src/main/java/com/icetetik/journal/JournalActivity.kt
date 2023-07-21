package com.icetetik.journal

import android.app.Activity
import android.app.Instrumentation.ActivityResult
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.icetetik.MoodActivity
import com.icetetik.authentication.signup.SignUpActivity
import com.icetetik.data.model.Mood
import com.icetetik.data.model.MoodItemView
import com.icetetik.databinding.ActivityJournalBinding
import com.icetetik.util.Helper
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
    private var moodCondition: String = ""
    private var moodNote: String = ""

    private val getResultChosenMood = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val moodItemView =
                it.data?.getParcelableExtra<MoodItemView>(KeyParcelable.MOOD_CONDITION)
            moodCondition = moodItemView?.condition ?: ""
            addMoodData()
        }
    }

    private val getResultNoteMood = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            moodNote = it.data?.getStringExtra(KeyParcelable.MOOD_NOTE) ?: ""
            addMoodData()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJournalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getUserSession { email ->
            if (email == null) {

            } else {
                userEmail = email
                val layoutManager = GridLayoutManager(this@JournalActivity, 7)

                binding.rvCalendar.layoutManager = layoutManager
                binding.rvCalendar.adapter = adapter

                setMonthView()
                updateCard()
                loadMoodData()

                setButtonAction()

                setObserver()
            }
        }
    }

    private fun loadMoodData(){
        if (userEmail.isEmpty()) {
            Toast.makeText(this, "Empty Email Session", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.getMood(userEmail, selectedDate)
        }
    }

    private fun setButtonAction(){
        binding.apply {
            btnClose.setOnClickListener {
                onBackPressed()
            }


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
    }

    private fun updateCard(){
        if(moodCondition.isEmpty() && moodNote.isEmpty()){
            binding.llParentCard.visibility = View.GONE
            binding.btnAddMood.setText("Tambah Mood")
            binding.btnAddNote.setText("Tambah Catatan")
        } else {
            binding.llParentCard.visibility = View.VISIBLE

            //check mood condition
            if(moodCondition.isEmpty()){
                binding.btnAddMood.setText("Tambah Mood")
                binding.llMoodConditionCard.visibility = View.GONE
            } else {
                binding.llMoodConditionCard.visibility = View.VISIBLE
                binding.tvMoodCondition.text = moodCondition
                binding.ivMoodCondition.setImageResource(Helper.mapMoodConditionToDrawable(moodCondition))
                binding.btnAddMood.setText("Edit Mood")
            }

            //check mood note
            if (moodNote.isEmpty()){
                binding.btnAddNote.setText("Tambah Catatan")
                binding.llMoodNoteCard.visibility = View.GONE
            } else {
                binding.llMoodNoteCard.visibility = View.VISIBLE
                binding.tvMoodNote.text = moodNote
                binding.btnAddNote.setText("Edit Catatan")
            }
        }
    }

    private fun setObserver() {
        viewModel.mood.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    Toast.makeText(this, "Loading", Toast.LENGTH_SHORT).show()
                }

                is UiState.Failure -> {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                    moodNote = ""
                    moodCondition = ""
                    updateCard()
                }

                is UiState.Success -> {
                    Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                    moodNote = state.data.note
                    moodCondition = state.data.condition
                    updateCard()
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
                    updateCard()
                }
            }
        }
    }

    private fun addMoodData() {
        if (userEmail.isEmpty()) {
            Toast.makeText(this, "Empty Email Session", Toast.LENGTH_SHORT).show()
        } else {

            val mood = Mood(
                posted = "${selectedDate.year}-${selectedDate.monthValue}-${selectedDate.dayOfMonth}\"",
                condition = moodCondition,
                note = moodNote
            )

            viewModel.addMood(
                userEmail = userEmail,
                mood = mood,
                uploadDate = selectedDate
            )
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
        loadMoodData()
        updateCard()
        setMonthView()
    }

}