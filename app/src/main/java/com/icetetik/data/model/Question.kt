package com.icetetik.data.model

data class Question(
    val id: Int = 0,
    val text: String = ""
)

data class QuestionResponse(
    val questions: ArrayList<Question> = ArrayList()
)
