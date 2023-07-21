package com.icetetik.util

import com.icetetik.R

object Helper {
    fun mapMoodConditionToDrawable(moodCondition: String) : Int {
        return when(moodCondition){
            "Senang" -> R.drawable.img_happy
            "Sedih" -> R.drawable.img_sad
            "Marah" -> R.drawable.img_angry
            "Terkejut" -> R.drawable.img_shock
            "Takut" -> R.drawable.img_scared
            "Jijik" -> R.drawable.img_disgusting
            else -> R.drawable.img_brush
        }
    }
}