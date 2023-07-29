package com.icetetik.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

data class Question(
    val id: Int = 0,
    val text: String = "",
    val category: String = ""
)

data class QuestionResponse(
    val questions: ArrayList<Question> = ArrayList()
)

@Parcelize
data class QuestionnaireResult(
    val stress: String = "",
    val worry: String = "",
    val depression: String = "",
    val posted: Timestamp = Timestamp.now(),
    val rawData: HashMap<String, Int> = HashMap()
): Parcelable

