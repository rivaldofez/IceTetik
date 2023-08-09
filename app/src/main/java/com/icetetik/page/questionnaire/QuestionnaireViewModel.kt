package com.icetetik.page.questionnaire

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.icetetik.data.model.Option
import com.icetetik.data.model.Question
import com.icetetik.data.model.QuestionnaireResult
import com.icetetik.data.repository.AuthRepository
import com.icetetik.data.repository.QuestionnaireRepository
import com.icetetik.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class QuestionnaireViewModel @Inject constructor(
    private val questionnaireRepository: QuestionnaireRepository,
    private val authRepository: AuthRepository
): ViewModel() {

    private val _questions = MutableLiveData<UiState<List<Question>>>()
    val questions: LiveData<UiState<List<Question>>>
        get() = _questions

    private val _options = MutableLiveData<UiState<List<Option>>>()
    val options: LiveData<UiState<List<Option>>>
        get() = _options

    private val _addQuestionnaireResult = MutableLiveData<UiState<String>>()
    val addQuestionnaireResult: LiveData<UiState<String>>
        get() = _addQuestionnaireResult


    fun getQuestions(){
        _questions.value = UiState.Loading
        questionnaireRepository.getQuestions { _questions.value = it }
    }

    fun addQuestionnaireResult(userEmail: String, questionnaireResult: QuestionnaireResult, uploadDate: LocalDate){
        _addQuestionnaireResult.value = UiState.Loading
        questionnaireRepository.addQuestionnaireResult(userEmail = userEmail, questionnaireResult = questionnaireResult, uploadDate = uploadDate){
            _addQuestionnaireResult.value = it
        }
    }

    fun getOptions(){
        _options.value = UiState.Loading
        questionnaireRepository.getOptions { _options.value = it }
    }

    fun getUserSession(result: (String?) -> Unit) {
        authRepository.getUserSession(result)
    }
}