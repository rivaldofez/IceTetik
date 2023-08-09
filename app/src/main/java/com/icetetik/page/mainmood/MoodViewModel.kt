package com.icetetik.page.mainmood

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
class MoodViewModel @Inject constructor(
    private val moodRepository: MoodRepository,
    private val authRepository: AuthRepository
): ViewModel(){

    private val _mood = MutableLiveData<UiState<Mood?>>()
    val mood: LiveData<UiState<Mood?>>
        get() = _mood

    fun getUserSession(result: (String?) -> Unit) {
        authRepository.getUserSession(result)
    }

    fun getMood(userEmail: String, uploadDate: LocalDate){
        _mood.value = UiState.Loading
        moodRepository.loadMood(userEmail = userEmail, uploadDate = uploadDate){
            _mood.value = it
        }
    }
}