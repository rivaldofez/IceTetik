package com.icetetik.page.questionnaire

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.Timestamp
import com.icetetik.R
import com.icetetik.data.model.Option
import com.icetetik.data.model.Question
import com.icetetik.data.model.QuestionnaireResult
import com.icetetik.databinding.ActivityQuestionnaireBinding
import com.icetetik.databinding.SublayoutAlertDialogBinding
import com.icetetik.databinding.SublayoutDialogConfirmationBinding
import com.icetetik.util.Extension.animateChangeVisibility
import com.icetetik.util.Extension.showSnackBar
import com.icetetik.util.KeyParcelable
import com.icetetik.util.UiState
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.time.ZoneOffset
import java.util.Date

@AndroidEntryPoint
class QuestionnaireActivity : AppCompatActivity() {
    private lateinit var binding: ActivityQuestionnaireBinding
    private val viewModel: QuestionnaireViewModel by viewModels()
    private val questions = ArrayList<Question>()
    private val answers = HashMap<Int, Int>()
    private var currentQuestion = 1
    private var userEmail: String = ""
    private var questionnaireResult: QuestionnaireResult? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuestionnaireBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getUserSession { email ->
            if (email == null) {
                binding.showSnackBar("Session Expired")
            } else {
                userEmail = email
                setObserverData()
                setButtonActions()
            }
        }
    }

    private fun setButtonActions() {
        binding.apply {
            btnNext.setOnClickListener {
                if (currentQuestion-1 < questions.size-1) {
                    val currAnswer = answers.get(currentQuestion)

                    if (currAnswer == null) {
                        showAlertDialog("Kamu harus memilih salah satu dari opsi yang ada")
                    } else {
                        currentQuestion++
                        changeCurrentQuestion()
                    }
                } else {
                    showConfirmationDialog("Apakah kamu sudah yakin mengisi setiap pertanyaan?")
                }
            }

            btnPrev.setOnClickListener {
                if (currentQuestion > 0) {
                    currentQuestion--
                    changeCurrentQuestion()
                }
            }

            btnOption1.setOnClickListener {
                answers.put(currentQuestion, 0)
                setOptionButtonState(0)
            }

            btnOption2.setOnClickListener {
                answers.put(currentQuestion, 1)
                setOptionButtonState(1)
            }

            btnOption3.setOnClickListener {
                answers.put(currentQuestion, 2)
                setOptionButtonState(2)
            }

            btnOption4.setOnClickListener {
                answers.put(currentQuestion, 3)
                setOptionButtonState(3)
            }
        }
    }


    private fun setObserverData() {
        viewModel.addQuestionnaireResult.observe(this){ state ->
            when (state) {
                is UiState.Loading -> {
                    showLoading(isLoading = true)
                }

                is UiState.Failure -> {
                    showLoading(isLoading = false)
                    binding.showSnackBar(getString(R.string.error_process_request))
                }

                is UiState.Success -> {
                    showLoading(isLoading = false)

                    if (questionnaireResult == null){
                        //handle null result
                    } else {
                        val intent = Intent(this@QuestionnaireActivity, ResultQuestionnaireActivity::class.java)
                        intent.putExtra(KeyParcelable.QUESTIONNAIRE_RESULT, questionnaireResult)
                        startActivity(intent)
                        finish()
                    }


                }
            }
        }


        viewModel.questions.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    showLoading(isLoading = true)
                }

                is UiState.Failure -> {
                    showLoading(isLoading = false)
                }

                is UiState.Success -> {
                    showLoading(isLoading = false)
                    setQuestionData(state.data)
                }
            }
        }

        viewModel.options.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    showLoading(isLoading = true)
                }

                is UiState.Failure -> {
                    showLoading(isLoading = false)
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }

                is UiState.Success -> {
                    showLoading(isLoading = false)
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

    private fun changeCurrentQuestion() {
        binding.titleQuestion.text = questions[currentQuestion-1].text
        val currAnswer = answers.get(currentQuestion)

        if (currAnswer == null) {
            setOptionButtonState(null)
        } else {
            setOptionButtonState(currAnswer)
        }
    }

    private fun setOptionButtonState(option: Int?) {
        binding.apply {
            if (option == null) {
                changeButtonStatus(btnOption1, false)
                changeButtonStatus(btnOption2, false)
                changeButtonStatus(btnOption3, false)
                changeButtonStatus(btnOption4, false)
            } else {
                when (option) {
                    0 -> {
                        changeButtonStatus(btnOption1, true)
                        changeButtonStatus(btnOption2, false)
                        changeButtonStatus(btnOption3, false)
                        changeButtonStatus(btnOption4, false)
                    }

                    1 -> {
                        changeButtonStatus(btnOption1, false)
                        changeButtonStatus(btnOption2, true)
                        changeButtonStatus(btnOption3, false)
                        changeButtonStatus(btnOption4, false)
                    }

                    2 -> {
                        changeButtonStatus(btnOption1, false)
                        changeButtonStatus(btnOption2, false)
                        changeButtonStatus(btnOption3, true)
                        changeButtonStatus(btnOption4, false)
                    }

                    3 -> {
                        changeButtonStatus(btnOption1, false)
                        changeButtonStatus(btnOption2, false)
                        changeButtonStatus(btnOption3, false)
                        changeButtonStatus(btnOption4, true)
                    }
                }
            }
        }
    }

    private fun changeButtonStatus(button: Button, state: Boolean) {
        if (state) {
            button.setTextColor(getColor(R.color.white))
            button.background = getDrawable(R.drawable.fr_button_option_questionnaire_selected)
        } else {
            button.setTextColor(getColor(R.color.primaryBackgroundColor))
            button.background = getDrawable(R.drawable.fr_button_option_questionnaire)
        }
    }

    private fun setOptionsData(data: List<Option>) {
        binding.apply {
            btnOption1.setText(data[0].text)
            btnOption2.setText(data[1].text)
            btnOption3.setText(data[2].text)
            btnOption4.setText(data[3].text)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            sblLoading.root.animateChangeVisibility(isLoading)
            if (isLoading) sblLoading.lottieLoading.playAnimation() else sblLoading.lottieLoading.pauseAnimation()
        }
    }


    private fun showAlertDialog(message: String) {
        val dialogBinding = SublayoutAlertDialogBinding.inflate(layoutInflater)

        val dialog = Dialog(this@QuestionnaireActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.apply {
            tvDialogMessage.text = message

            btnOk.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun showConfirmationDialog(message: String) {
        val dialogBinding = SublayoutDialogConfirmationBinding.inflate(layoutInflater)

        val dialog = Dialog(this@QuestionnaireActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialogBinding.apply {
            tvDialogMessage.text = message

            btnYes.setOnClickListener {
                questionnaireResult = calculateResult()
                    questionnaireResult?.let {
                        viewModel.addQuestionnaireResult(userEmail = userEmail, questionnaireResult = it, uploadDate = LocalDate.now())
                    }
            }

            btnNo.setOnClickListener {
                dialog.dismiss()
            }
        }
        dialog.show()
    }

    private fun calculateResult(): QuestionnaireResult {
        var totalStress = 0
        var totalWorry = 0
        var totalDepression = 0

        questions.forEach { question ->
            val answer = answers.get(question.id)
            if (answer == null) {
                //handle null
            } else {
                if (question.category == "Stress") {
                    totalStress += answer
                } else if (question.category == "Kecemasan") {
                    totalWorry += answer
                } else if (question.category == "Depresi") {
                    totalDepression += answer
                }
            }
        }

        return QuestionnaireResult(
            stress = stressScale(totalStress),
            depression = depressionScale(totalDepression),
            worry = worryScale(totalWorry),
            posted = Timestamp(
                Date.from(
                    LocalDate.now().atStartOfDay().toInstant(ZoneOffset.UTC)
                )
            ),
            rawData = answers.mapKeys { it.key.toString() } as HashMap<String, Int>
        )
    }

    private fun stressScale(totalStress: Int): String {
        if (totalStress > 34) {
            return "Sangat Parah"
        } else if (totalStress > 25) {
            return "Parah"
        } else if (totalStress > 18) {
            return "Sedang"
        } else if (totalStress > 14) {
            return "Ringan"
        } else {
            return "Normal"
        }
    }

    private fun depressionScale(totalDepression: Int): String {
        if (totalDepression > 28) {
            return "Sangat Parah"
        } else if (totalDepression > 20) {
            return "Parah"
        } else if (totalDepression > 13) {
            return "Sedang"
        } else if (totalDepression > 9) {
            return "Ringan"
        } else {
            return "Normal"
        }
    }

    private fun worryScale(totalWorry: Int): String {
        if (totalWorry > 20) {
            return "Sangat Parah"
        } else if (totalWorry > 14) {
            return "Parah"
        } else if (totalWorry > 9) {
            return "Sedang"
        } else if (totalWorry > 7) {
            return "Ringan"
        } else {
            return "Normal"
        }
    }


    override fun onStart() {
        super.onStart()
        viewModel.getOptions()
        viewModel.getQuestions()
    }
}