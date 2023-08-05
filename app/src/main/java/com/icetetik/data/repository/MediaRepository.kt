package com.icetetik.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.icetetik.data.model.Infographic
import com.icetetik.data.model.InfographicResponse
import com.icetetik.data.model.Video
import com.icetetik.data.model.VideoResponse
import com.icetetik.util.DummyQuestion
import com.icetetik.util.FireStoreCollection
import com.icetetik.util.FireStoreDocument
import com.icetetik.util.UiState

class MediaRepository(
    private val database: FirebaseFirestore
) {

    fun setVideos(result: (UiState<String>) -> Unit) {
        val listVideo = ArrayList<Video>()
        listVideo.addAll(
            DummyQuestion.generateVideos()
        )

        database.collection(FireStoreCollection.APPS)
            .document(FireStoreDocument.VIDEOS)
            .set(VideoResponse(videos = listVideo))
            .addOnSuccessListener {
                result.invoke(UiState.Success("Succes set data video"))
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    fun setInfographics(result: (UiState<String>) -> Unit) {
        val listInfographic = ArrayList<Infographic>()
        listInfographic.addAll(
            DummyQuestion.generateInfographics()
        )

        database.collection(FireStoreCollection.APPS)
            .document(FireStoreDocument.INFOGRAPHICS)
            .set(InfographicResponse(infographics = listInfographic))
            .addOnSuccessListener {
                result.invoke(UiState.Success("Succes set data infographic"))
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    fun getInfographics(result: (UiState<List<Infographic>>) -> Unit) {
        database.collection(FireStoreCollection.APPS)
            .document(FireStoreDocument.INFOGRAPHICS)
            .get()
            .addOnSuccessListener { snapshot ->
                val dataResult = snapshot.toObject(InfographicResponse::class.java)

                if (dataResult == null) {
                    result.invoke(UiState.Failure("data empty"))
                } else {
                    result.invoke(UiState.Success(dataResult.infographics))
                }
            }
            .addOnFailureListener {
                result.invoke(
                    UiState.Failure(
                        it.localizedMessage
                    )
                )
            }
    }

    fun getVideos(result: (UiState<List<Video>>) -> Unit) {
        database.collection(FireStoreCollection.APPS)
            .document(FireStoreDocument.VIDEOS)
            .get()
            .addOnSuccessListener { snapshot ->
                val dataResult = snapshot.toObject(VideoResponse::class.java)

                if (dataResult == null) {
                    result.invoke(UiState.Failure("data empty"))
                } else {
                    result.invoke(UiState.Success(dataResult.videos))
                }
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