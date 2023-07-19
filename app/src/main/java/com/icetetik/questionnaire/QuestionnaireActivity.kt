package com.icetetik.questionnaire

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.icetetik.R
import com.icetetik.data.model.Option
import com.icetetik.data.model.Question
import com.icetetik.databinding.ActivityQuestionnaireBinding
import com.icetetik.util.DummyQuestion
import com.icetetik.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestionnaireActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionnaireBinding
    private val viewModel: QuestionnaireViewModel by viewModels()

    private val questions = ArrayList<Question>()
    private var currentQuestion = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuestionnaireBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObserverData()

        binding.btnNext.setOnClickListener {
            currentQuestion++
            changeCurrentQuestion()
        }
    }

    private fun setObserverData(){
        viewModel.questions.observe(this) { state ->
            when(state){
                is UiState.Loading -> {
                    Toast.makeText( this, "Loading", Toast.LENGTH_SHORT).show()
                }

                is UiState.Failure -> {
                    Toast.makeText( this, "Error", Toast.LENGTH_SHORT).show()
                }

                is UiState.Success -> {
                    setQuestionData(state.data)
                }
            }
        }

        viewModel.options.observe(this) { state ->
            when(state){
                is UiState.Loading -> {
                    Toast.makeText( this, "Loading", Toast.LENGTH_SHORT).show()
                }

                is UiState.Failure -> {
                    Toast.makeText( this, "Error", Toast.LENGTH_SHORT).show()
                }

                is UiState.Success -> {
                    setOptionsData(state.data)
                }
            }
        }
    }


    private fun setQuestionData(data: List<Question>) {
        questions.clear()
        questions.addAll(data)

        changeCurrentQuestion()
    }

    private fun changeCurrentQuestion(){
        binding.titleQuestion.text = questions[currentQuestion].text
    }

    private fun setOptionsData(data: List<Option>){
        binding.apply {
            btnOption1.setText(data[0].text)
            btnOption2.setText(data[1].text)
            btnOption3.setText(data[2].text)
            btnOption4.setText(data[3].text)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getOptions()
        viewModel.getQuestions()
    }

}