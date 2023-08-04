package com.icetetik.util

import androidx.datastore.preferences.core.stringPreferencesKey
import com.icetetik.R
import com.icetetik.data.model.MoodCondition
import com.icetetik.data.model.MoodItemView
import com.icetetik.data.model.Question

object FireStoreCollection{
    val USER = "user"
    val APPS = "apps"
    val MOODS = "moods"
    val QUESTIONNAIRE = "questionnaire"
}

object FireStoreDocument {
    val QUESTIONS = "questions"
    val OPTIONS = "options"
    val VIDEOS = "videos"
    val INFOGRAPHICS = "infographics"
}

object FirestoreDocumentField {
    val QUESTION_DATA = "question-data"
    val OPTION_DATA = "option-data"
    val CONDITION = "condition"
    val POSTED = "posted"
}

object KeyParcelable {
    val MOOD_CONDITION = "mood_condition"
    val MOOD_NOTE = "mood_note"
    val QUESTIONNAIRE_RESULT = "questionnaire_result"
    val VIDEO_DATA = "video_data"

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
