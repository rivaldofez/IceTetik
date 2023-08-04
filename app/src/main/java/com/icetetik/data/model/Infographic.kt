package com.icetetik.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Infographic(
    val id: String = "",
    val title: String = "",
    val url: String = ""
): Parcelable

data class InfographicResponse(
    val infographics: ArrayList<Infographic> = ArrayList()
)