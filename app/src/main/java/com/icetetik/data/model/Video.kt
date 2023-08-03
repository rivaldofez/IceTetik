package com.icetetik.data.model

data class Video(
    val id: Int = 0,
    val title: String = "",
    val url: String = ""
)

data class VideoResponse(
    val videos: ArrayList<Video> = ArrayList()
)