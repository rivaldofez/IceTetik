package com.icetetik.page.journal.mood

import com.icetetik.data.model.MoodItemView

interface MoodItemCallback {
    fun onItemMoodCallback(moodItemView: MoodItemView)
}