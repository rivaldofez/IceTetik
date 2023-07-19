package com.icetetik.data.model

import java.util.Date

data class Mood(
    val posted: Date,
    val note: String,
    val condition: String
)