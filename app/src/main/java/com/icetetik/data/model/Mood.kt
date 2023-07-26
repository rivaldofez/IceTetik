package com.icetetik.data.model

import android.os.Parcelable
import com.google.firebase.Timestamp
import kotlinx.parcelize.Parcelize

data class Mood(
    val posted: Timestamp = Timestamp.now(),
    val note: String = "",
    val condition: String = ""
)

@Parcelize
data class MoodItemView(
    val condition: MoodCondition,
    val image: Int
): Parcelable