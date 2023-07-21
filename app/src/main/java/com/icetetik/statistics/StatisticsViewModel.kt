package com.icetetik.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.icetetik.data.model.Mood
import com.icetetik.data.repository.AuthRepository
import com.icetetik.data.repository.MoodRepository
import com.icetetik.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    val moodRepository: MoodRepository,
    val authRepository: AuthRepository
): ViewModel() {


    private val _monthlyMood = MutableLiveData<UiState<List<Mood>>>()
    val monthlyMood: LiveData<UiState<List<Mood>>>
        get() = _monthlyMood

    fun getUserSession(result: (String?) -> Unit) {
        authRepository.getUserSession(result)
    }

    fun fetchMonthlyMood(userEmail: String, baseDate: LocalDate){
        _monthlyMood.value = UiState.Loading
        moodRepository.fetchMonthlyMood(userEmail = userEmail, baseDate = baseDate) {
            _monthlyMood.value = it
        }
    }

}