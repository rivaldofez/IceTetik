package com.icetetik.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Video(
    val id: String = "",
    val title: String = "",
    val url: String = ""
): Parcelable

data class VideoResponse(
    val videos: ArrayList<Video> = ArrayList()
)