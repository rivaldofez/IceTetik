package com.icetetik.page.video

import com.icetetik.data.model.Video

interface VideoItemCallback {
    fun onItemVideoClick(video: Video)
}