package com.icetetik.journal

import com.icetetik.data.model.MoodItemView

interface MoodItemCallback {
    fun onItemMoodCallback(moodItemView: MoodItemView)
}