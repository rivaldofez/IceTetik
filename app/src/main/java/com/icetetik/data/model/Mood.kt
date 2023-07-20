package com.icetetik.data.model

import java.time.LocalDate
import java.util.Date

data class Mood(
    val posted: String = "",
    val note: String = "",
    val condition: String = ""
)

data class MoodItemView(
    val condition: String,
    val image: Int
)