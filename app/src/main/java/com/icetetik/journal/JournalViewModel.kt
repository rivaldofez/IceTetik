package com.icetetik.journal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.icetetik.data.model.Mood
import com.icetetik.data.repository.MoodRepository
import com.icetetik.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class JournalViewModel @Inject constructor(
    val moodRepository: MoodRepository
): ViewModel(){

    private val _addMood = MutableLiveData<UiState<String>>()
    val addMood: LiveData<UiState<String>>
        get() = _addMood

    fun addMood(userEmail: String, mood: Mood){
        _addMood.value = UiState.Loading
        moodRepository.addMood(userEmail = userEmail, mood = mood){
            _addMood.value = it
        }
    }

}