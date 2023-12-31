package com.icetetik.page.questionnaire

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.icetetik.R
import com.icetetik.data.model.QuestionnaireResult
import com.icetetik.databinding.ActivityResultQuestionnaireBinding
import com.icetetik.util.Extension.showSnackBar
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
            intent.getParcelableExtra(KeyParcelable.QUESTIONNAIRE_RESULT)
        }

        if(questionnaireResult == null){
            binding.showSnackBar(getString(R.string.error_questionnaire_null))
        } else {
            binding.apply {
                tvResultDepression.text = questionnaireResult.depression
                tvResultStress.text = questionnaireResult.stress
                tvResultWorry.text = questionnaireResult.worry

                btnNext.setOnClickListener {
                    val intent = Intent(this@ResultQuestionnaireActivity, OnboardVideoInfoActivity::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}