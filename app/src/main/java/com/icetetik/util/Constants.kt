package com.icetetik.util

import androidx.datastore.preferences.core.stringPreferencesKey
import com.icetetik.R
import com.icetetik.data.model.MoodCondition
import com.icetetik.data.model.MoodItemView
import com.icetetik.data.model.Question

object FireStoreCollection{
    const val USER = "user"
    const val APPS = "apps"
    const val MOODS = "moods"
    const val QUESTIONNAIRE = "questionnaire"
}

object FireStoreDocument {
    const val QUESTIONS = "questions"
    const val OPTIONS = "options"
    const val VIDEOS = "videos"
    const val INFOGRAPHICS = "infographics"
}

object FirestoreDocumentField {
    const val QUESTION_DATA = "question-data"
    const val OPTION_DATA = "option-data"
    const val CONDITION = "condition"
    const val POSTED = "posted"
}

object KeyParcelable {
    const val MOOD_CONDITION = "mood_condition"
    const val MOOD_NOTE = "mood_note"
    const val QUESTIONNAIRE_RESULT = "questionnaire_result"
    const val VIDEO_DATA = "video_data"

}

object KeyPreferences {
    val THEME_KEY = stringPreferencesKey("theme_key")
}

object MoodConstants {
    val moodItemViews = listOf(
        MoodItemView(condition = MoodCondition.HAPPY, image = R.drawable.img_happy),
        MoodItemView(condition = MoodCondition.SAD, image = R.drawable.img_sad),
        MoodItemView(condition = MoodCondition.ANGRY, image = R.drawable.img_angry),
        MoodItemView(condition = MoodCondition.SHOCK, image = R.drawable.img_shock),
        MoodItemView(condition = MoodCondition.SCARED, image = R.drawable.img_scared),
        MoodItemView(condition = MoodCondition.DISGUSTING, image = R.drawable.img_disgusting)
    )
}
