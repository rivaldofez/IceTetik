package com.icetetik.data.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.icetetik.data.model.Option
import com.icetetik.data.model.Question
import com.icetetik.util.FireStoreCollection
import com.icetetik.util.FireStoreDocument
import com.icetetik.util.FirestoreDocumentField
import com.icetetik.util.UiState

class QuestionnaireRepository(
    val database: FirebaseFirestore
) {

    fun getQuestions(result: (UiState<List<Question>>) -> Unit){
        database.collection(FireStoreCollection.APPS)
            .document(FireStoreDocument.QUESTIONS)
            .get()
            .addOnSuccessListener { snapshot ->
                val questions = snapshot.data?.get(FirestoreDocumentField.QUESTION_DATA) as List<Question>?
                if (questions == null){
                    UiState.Failure("data empty")
                } else {
                    UiState.Success(questions)
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
                val options = snapshot.data?.get(FirestoreDocumentField.OPTION_DATA) as List<Option>?
                if (options == null){
                    UiState.Failure("data empty")
                } else {
                    UiState.Success(options)
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