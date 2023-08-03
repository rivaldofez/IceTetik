package com.icetetik.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.icetetik.util.UiState

class VideoRepository(
    private val database: FirebaseFirestore
) {

    fun setVideos(result: (UiState<String>) -> Unit) {

    }
}