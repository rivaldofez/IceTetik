package com.icetetik.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.icetetik.data.model.Mood
import com.icetetik.data.repository.MoodRepository
import com.icetetik.util.UiState
import java.time.LocalDate
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
    val moodRepository: MoodRepository
): ViewModel() {


    private val _monthlyMood = MutableLiveData<UiState<List<Mood>>>()
    val monthlyMood: LiveData<UiState<List<Mood>>>
        get() = _monthlyMood

    fun fetchMonthlyMood(userEmail: String, baseDate: LocalDate){
        _monthlyMood.value = UiState.Loading
        moodRepository.fetchMonthlyMood(userEmail = userEmail, baseDate = baseDate) {
            _monthlyMood.value = it
        }
    }

}