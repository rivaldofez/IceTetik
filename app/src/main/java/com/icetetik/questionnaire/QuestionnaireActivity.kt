package com.icetetik.questionnaire

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.icetetik.R
import com.icetetik.databinding.ActivityQuestionnaireBinding
import com.icetetik.util.DummyQuestion

class QuestionnaireActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionnaireBinding
    private val questions = DummyQuestion.generateQuestions()
    private var currentQuestion = 1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuestionnaireBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setQuestionData()
        setOptionsData()

        binding.btnNext.setOnClickListener {
            currentQuestion++
            setQuestionData()
        }
    }

    private fun setQuestionData() {
        binding.titleQuestion.text = questions[currentQuestion].text
    }

    private fun setOptionsData(){
        binding.apply {
            val options = DummyQuestion.generateOptions()
            btnOption1.setText(options[0].text)
            btnOption2.setText(options[1].text)
            btnOption3.setText(options[2].text)
            btnOption4.setText(options[3].text)
        }
    }
}