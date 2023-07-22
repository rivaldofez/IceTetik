package com.icetetik.journal

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.Timestamp
import com.icetetik.R
import com.icetetik.data.model.Mood
import com.icetetik.data.model.MoodItemView
import com.icetetik.databinding.ActivityJournalBinding
import com.icetetik.journal.calendar.CalendarAdapter
import com.icetetik.journal.calendar.CalendarItemCallback
import com.icetetik.journal.mood.MoodChooserActivity
import com.icetetik.journal.note.MoodNoteWriterActivity
import com.icetetik.util.Extension.animateChangeVisibility
import com.icetetik.util.Extension.showSnackBar
import com.icetetik.util.Helper
import com.icetetik.util.KeyParcelable
import com.icetetik.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneOffset
import java.util.Date

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
            moodCondition = moodItemView?.condition?.title ?: ""
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
                binding.showSnackBar("Session Expired")
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

    private fun loadMoodData() {
        if (userEmail.isEmpty()) {
            binding.showSnackBar("Session Expired")
        } else {
            viewModel.getMood(userEmail, selectedDate)
        }
    }

    private fun setButtonAction() {
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
                intent.putExtra(KeyParcelable.MOOD_NOTE, moodNote)
                getResultNoteMood.launch(intent)
            }

            llMoodNoteCard.setOnClickListener {
                val intent = Intent(this@JournalActivity, MoodNoteWriterActivity::class.java)
                intent.putExtra(KeyParcelable.MOOD_NOTE, moodNote)
                getResultNoteMood.launch(intent)
            }
        }
    }

    private fun updateCard() {
        if (moodCondition.isEmpty() && moodNote.isEmpty()) {
            binding.llParentCard.animateChangeVisibility(false)


            binding.btnAddMood.text = "Tambah Mood"
            binding.btnAddNote.text = "Tambah Catatan"
        } else {
            binding.llParentCard.animateChangeVisibility(true)

            //check mood condition
            if (moodCondition.isEmpty()) {
                binding.btnAddMood.text = "Tambah Mood"

                binding.llMoodConditionCard.animateChangeVisibility(false)
            } else {
                binding.llMoodConditionCard.animateChangeVisibility(true)
                binding.tvMoodCondition.text = getString(R.string.plc_today_mood, moodCondition)
                binding.ivMoodCondition.setImageResource(
                    Helper.mapMoodConditionToDrawable(
                        moodCondition
                    )
                )
                binding.btnAddMood.text = "Edit Mood"
            }

            //check mood note
            if (moodNote.isEmpty()) {
                binding.btnAddNote.setText("Tambah Catatan")
                binding.llMoodNoteCard.animateChangeVisibility(false)
            } else {
                binding.llMoodNoteCard.animateChangeVisibility(true)
                binding.tvMoodNote.text = moodNote
                binding.btnAddNote.setText("Edit Catatan")
            }
        }
    }

    private fun setObserver() {
        viewModel.mood.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    showLoading(isLoading = true)
                }

                is UiState.Failure -> {
                    showLoading(isLoading = false)
                    binding.showSnackBar("There's error occured while process your request")
                    moodNote = ""
                    moodCondition = ""
                    updateCard()
                }

                is UiState.Success -> {
                    showLoading(isLoading = false)

                    val result = state.data
                    if (result == null) {
                        moodNote = ""
                        moodCondition = ""
                        updateCard()

                        binding.showSnackBar("Mood atau Jurnal belum ditambahkan")
                    } else {
                        moodNote = result.note
                        moodCondition = result.condition
                        updateCard()
                    }
                }
            }
        }

        viewModel.addMood.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    showLoading(isLoading = true)
                }

                is UiState.Failure -> {
                    showLoading(isLoading = false)
                    binding.showSnackBar("There's error occured while process your request")
                }

                is UiState.Success -> {
                    showLoading(isLoading = false)
                    updateCard()
                }
            }
        }
    }

    private fun addMoodData() {
        if (userEmail.isEmpty()) {
            binding.showSnackBar("Session Expired")
        } else {
            val mood = Mood(
                posted = Timestamp(
                    Date.from(
                        selectedDate.atStartOfDay().toInstant(ZoneOffset.UTC)
                    )
                ),
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

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            sblLoading.root.animateChangeVisibility(isLoading)
            btnNextMonth.isEnabled = !isLoading
            btnPrevMonth.isEnabled = !isLoading
            rvCalendar.isEnabled = !isLoading
            btnAddMood.isEnabled = !isLoading
            btnAddNote.isEnabled = !isLoading
        }
    }

    private fun setMonthView() {
        binding.tvMonth.text = Helper.monthYearFromDate(currentAdapterDate)
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

    override fun onItemCalendarClicked(date: String) {
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