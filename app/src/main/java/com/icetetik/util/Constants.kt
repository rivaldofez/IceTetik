package com.icetetik.util

import com.icetetik.data.model.Question

object FireStoreCollection{
    val USER = "user"
    val APPS = "apps"
    val MOODS = "moods"
}

object FireStoreDocument {
    val QUESTIONS = "questions"
    val OPTIONS = "options"
}

object FirestoreDocumentField {
    val QUESTION_DATA = "question-data"
    val OPTION_DATA = "option-data"
}
