package com.icetetik.page.questionnaire

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.icetetik.R
import com.icetetik.data.model.QuestionnaireResult
import com.icetetik.databinding.ActivityResultQuestionnaireBinding
import com.icetetik.util.KeyParcelable

class ResultQuestionnaireActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultQuestionnaireBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultQuestionnaireBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val questionnaireResult = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(KeyParcelable.QUESTIONNAIRE_RESULT, QuestionnaireResult::class.java)
        } else {
            intent.getParcelableExtra<QuestionnaireResult>(KeyParcelable.QUESTIONNAIRE_RESULT)
        }

        if(questionnaireResult == null){

        } else {
            binding.apply {
                tvResultDepression.text = questionnaireResult.depression
                tvResultStress.text = questionnaireResult.stress
                tvResultWorry.text = questionnaireResult.worry
            }
        }
    }
}