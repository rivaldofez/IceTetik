package com.icetetik.page.questionnaire

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.icetetik.data.model.Option
import com.icetetik.data.model.Question
import com.icetetik.data.repository.QuestionnaireRepository
import com.icetetik.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuestionnaireViewModel @Inject constructor(
    val repository: QuestionnaireRepository
): ViewModel() {

    private val _questions = MutableLiveData<UiState<List<Question>>>()
    val questions: LiveData<UiState<List<Question>>>
        get() = _questions

    private val _options = MutableLiveData<UiState<List<Option>>>()
    val options: LiveData<UiState<List<Option>>>
        get() = _options


    fun getQuestions(){
        _questions.value = UiState.Loading
        repository.getQuestions { _questions.value = it }
    }

    fun getOptions(){
        _options.value = UiState.Loading
        repository.getOptions { _options.value = it }
    }
}