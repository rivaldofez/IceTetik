package com.icetetik.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.icetetik.data.model.Mood
import com.icetetik.data.model.Option
import com.icetetik.data.model.OptionResponse
import com.icetetik.data.model.Question
import com.icetetik.data.model.QuestionResponse
import com.icetetik.data.model.QuestionnaireResult
import com.icetetik.util.DummyQuestion
import com.icetetik.util.FireStoreCollection
import com.icetetik.util.FireStoreDocument
import com.icetetik.util.UiState
import java.time.LocalDate

class QuestionnaireRepository(
    private val database: FirebaseFirestore
) {

    fun addQuestionnaireResult(
        userEmail: String,
        questionnaireResult: QuestionnaireResult,
        uploadDate: LocalDate,
        result: (UiState<String>) -> Unit
    ) {
        val document = database.collection(FireStoreCollection.USER).document(userEmail)
            .collection(FireStoreCollection.QUESTIONNAIRE)
            .document(
                "${uploadDate.year}-${uploadDate.monthValue}-${uploadDate.dayOfMonth}"
            )

        document.set(questionnaireResult)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Berhasil menyimpan data kuesioner")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    fun loadQuestionnaireResult(userEmail: String, uploadDate: LocalDate, result: (UiState<QuestionnaireResult?>) -> Unit) {
        database.collection(FireStoreCollection.USER).document(userEmail)
            .collection(FireStoreCollection.QUESTIONNAIRE)
            .document(
                "${uploadDate.year}-${uploadDate.monthValue}-${uploadDate.dayOfMonth}"
            )
            .get()
            .addOnSuccessListener { snapshot ->
                val dataResult = snapshot.toObject(QuestionnaireResult::class.java)

                if (dataResult == null) {
                    result.invoke(UiState.Success(null))
                } else {
                    result.invoke(UiState.Success(dataResult))
                }
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }


    fun setAppQuestions(result: (UiState<String>) -> Unit){
        val listQuestion = ArrayList<Question>()
        listQuestion.addAll(
            DummyQuestion.generateQuestions()
        )

        database.collection(FireStoreCollection.APPS)
            .document(FireStoreDocument.QUESTIONS)
            .set(QuestionResponse(questions = listQuestion))
            .addOnSuccessListener {
                result.invoke(UiState.Success("Success set data question"))
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    fun setAppQuestionOptions(result: (UiState<String>) -> Unit){
        val listOption = ArrayList<Option>()
        listOption.addAll(
            DummyQuestion.generateOptions()
        )

        database.collection(FireStoreCollection.APPS)
            .document(FireStoreDocument.OPTIONS)
            .set(OptionResponse(options = listOption))
            .addOnSuccessListener {
                result.invoke(UiState.Success("Success set data option"))
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    fun getQuestions(result: (UiState<List<Question>>) -> Unit){
        database.collection(FireStoreCollection.APPS)
            .document(FireStoreDocument.QUESTIONS)
            .get()
            .addOnSuccessListener { snapshot ->
                val dataResult = snapshot.toObject(QuestionResponse::class.java)

                if (dataResult == null){
                    result.invoke(UiState.Failure("data empty"))
                } else {
                    result.invoke(UiState.Success(dataResult.questions))
                }
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    fun getOptions(result: (UiState<List<Option>>) -> Unit){
        database.collection(FireStoreCollection.APPS)
            .document(FireStoreDocument.OPTIONS)
            .get()
            .addOnSuccessListener { snapshot ->
                val dataResult = snapshot.toObject(OptionResponse::class.java)

                if (dataResult == null){
                    result.invoke(UiState.Failure("data empty"))
                } else {
                    result.invoke(UiState.Success(dataResult.options))
                }
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }
}

//firestore.collection("apps")
//.document("questions")
//.get()
//.addOnSuccessListener { snapshot ->
//    val mapData = snapshot.data
//    val question = mapData?.get("question-data") as List<Question>
//    val option = mapData?.get("options-data") as List<Option>
//
//    Log.d("Teston", question.toString())
//    Log.d("Teston", option.toString())
//
//}