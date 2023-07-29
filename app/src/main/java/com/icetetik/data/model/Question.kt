package com.icetetik.data.model

import com.google.firebase.Timestamp

data class Question(
    val id: Int = 0,
    val text: String = "",
    val category: String = ""
)

data class QuestionResponse(
    val questions: ArrayList<Question> = ArrayList()
)

data class QuestionnaireResult(
    val stress: String = "",
    val worry: String = "",
    val depression: String = "",
    val posted: Timestamp = Timestamp.now(),
    val rawData: HashMap<Int, Int> = HashMap()
)

