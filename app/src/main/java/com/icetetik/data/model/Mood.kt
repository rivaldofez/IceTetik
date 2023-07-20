package com.icetetik.data.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.util.Date

data class Mood(
    val posted: String = "",
    val note: String = "",
    val condition: String = ""
)

@Parcelize
data class MoodItemView(
    val condition: String,
    val image: Int
): Parcelable