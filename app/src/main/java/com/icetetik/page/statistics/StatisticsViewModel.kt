package com.icetetik.page.statistics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.icetetik.data.model.MoodCondition
import com.icetetik.data.repository.AuthRepository
import com.icetetik.data.repository.MoodRepository
import com.icetetik.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val moodRepository: MoodRepository,
    private val authRepository: AuthRepository
): ViewModel() {


    private val _monthlyMood = MutableLiveData<UiState<Map<MoodCondition, Int>>>()
    val monthlyMood: LiveData<UiState<Map<MoodCondition, Int>>>
        get() = _monthlyMood

    fun getUserSession(result: (String?) -> Unit) {
        authRepository.getUserSession(result)
    }

    fun fetchMonthlyMood(userEmail: String, baseDate: LocalDate){
        _monthlyMood.value = UiState.Loading
        moodRepository.fetchMoodStatisticsMonthly(userEmail = userEmail, baseDate = baseDate) {
            _monthlyMood.value = it
        }
    }

}