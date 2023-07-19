package com.icetetik.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.icetetik.data.model.Mood
import com.icetetik.data.model.User
import com.icetetik.util.FireStoreCollection
import com.icetetik.util.UiState

class MoodRepository(
    val database: FirebaseFirestore
) {
    fun addMood(userEmail: String, mood: Mood, result: (UiState<String>) -> Unit){
        val document = database.collection(FireStoreCollection.USER).document(userEmail)
            .collection(FireStoreCollection.MOODS)
            .document(mood.posted.toString())

        document.set(mood)
            .addOnSuccessListener {
                result.invoke(
                    UiState.Success("Success add mood data")
                )
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }

    }
}