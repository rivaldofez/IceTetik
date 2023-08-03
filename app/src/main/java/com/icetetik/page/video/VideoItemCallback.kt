package com.icetetik.page.video

import com.icetetik.data.model.MoodItemView
import com.icetetik.data.model.Video

interface VideoItemCallback {
    fun onItemVideoCallback(video: Video)
}