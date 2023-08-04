package com.icetetik.page.infographic

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.icetetik.data.model.Infographic
import com.icetetik.data.repository.MediaRepository
import com.icetetik.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InfographicViewModel @Inject constructor(
    private val mediaRepository: MediaRepository
): ViewModel() {

    private val _infographics = MutableLiveData<UiState<List<Infographic>>>()
    val infographics: LiveData<UiState<List<Infographic>>>
        get() = _infographics

    private val _addInfographics = MutableLiveData<UiState<String>>()
    val addInfographics: LiveData<UiState<String>>
        get() = _addInfographics


    fun getInfographics(){
        _infographics.value = UiState.Loading
        mediaRepository.getInfographics { _infographics.value = it }
    }

    fun addInfographics(){
        _addInfographics.value = UiState.Loading
        mediaRepository.setInfographics { _addInfographics.value = it }
    }


}