package com.icetetik.data.repository

import android.util.Log
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.icetetik.data.model.Mood
import com.icetetik.data.model.MoodCondition
import com.icetetik.data.model.OptionResponse
import com.icetetik.data.model.User
import com.icetetik.util.FireStoreCollection
import com.icetetik.util.FirestoreDocumentField
import com.icetetik.util.UiState
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.temporal.TemporalAdjusters
import java.util.Date

class MoodRepository(
    val database: FirebaseFirestore
) {
    fun addMood(
        userEmail: String,
        mood: Mood,
        uploadDate: LocalDate,
        result: (UiState<String>) -> Unit
    ) {
        val document = database.collection(FireStoreCollection.USER).document(userEmail)
            .collection(FireStoreCollection.MOODS)
            .document(
                "${uploadDate.year}-${uploadDate.monthValue}-${uploadDate.dayOfMonth}"
            )

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

    fun loadMood(userEmail: String, uploadDate: LocalDate, result: (UiState<Mood>) -> Unit) {
        database.collection(FireStoreCollection.USER).document(userEmail)
            .collection(FireStoreCollection.MOODS)
            .document(
                "${uploadDate.year}-${uploadDate.monthValue}-${uploadDate.dayOfMonth}"
            )
            .get()
            .addOnSuccessListener { snapshot ->
                val dataResult = snapshot.toObject(Mood::class.java)

                if (dataResult == null) {
                    result.invoke(UiState.Failure("data empty"))
                } else {
                    result.invoke(UiState.Success(dataResult))
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

    fun fetchMoodStatisticsMonthly(
        userEmail: String,
        baseDate: LocalDate,
        result: (UiState<Map<MoodCondition, Int>>) -> Unit
    ) {
        val start = Timestamp(
            Date.from(
                baseDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay().toInstant(
                    ZoneOffset.UTC
                )
            )
        )
        val end = Timestamp(
            Date.from(
                baseDate.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay().toInstant(
                    ZoneOffset.UTC
                )
            )
        )

        database.collection(FireStoreCollection.USER).document(userEmail)
            .collection(FireStoreCollection.MOODS)
            .whereGreaterThanOrEqualTo("posted", start)
            .whereLessThanOrEqualTo("posted", end)
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.isEmpty) {
                    result.invoke(UiState.Success(emptyMap()))
                } else {
                    var happy = 0
                    var sad = 0
                    var angry = 0
                    var shock = 0
                    var scared = 0
                    var disgusting = 0

                    for (document in snapshot.documents) {
                        when (document.get(FirestoreDocumentField.CONDITION).toString()) {
                            MoodCondition.HAPPY.title -> happy++
                            MoodCondition.SAD.title -> sad++
                            MoodCondition.ANGRY.title -> angry++
                            MoodCondition.SHOCK.title -> shock++
                            MoodCondition.SCARED.title -> scared++
                            MoodCondition.DISGUSTING.title -> disgusting++
                        }
                    }
                    result.invoke(
                        UiState.Success(
                            mapOf(
                                MoodCondition.HAPPY to happy,
                                MoodCondition.SAD to sad,
                                MoodCondition.ANGRY to angry,
                                MoodCondition.SHOCK to shock,
                                MoodCondition.SCARED to scared,
                                MoodCondition.DISGUSTING to disgusting
                            )
                        )
                    )
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

    fun fetchMonthlyMood(
        userEmail: String,
        baseDate: LocalDate,
        result: (UiState<List<Mood>>) -> Unit
    ) {
        val start = Timestamp(
            Date.from(
                baseDate.with(TemporalAdjusters.firstDayOfMonth()).atStartOfDay().toInstant(
                    ZoneOffset.UTC
                )
            )
        )
        val end = Timestamp(
            Date.from(
                baseDate.with(TemporalAdjusters.lastDayOfMonth()).atStartOfDay().toInstant(
                    ZoneOffset.UTC
                )
            )
        )

        database.collection(FireStoreCollection.USER).document(userEmail)
            .collection(FireStoreCollection.MOODS)
            .whereGreaterThanOrEqualTo("posted", start)
            .whereLessThanOrEqualTo("posted", end)
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.isEmpty) {
                    result.invoke(UiState.Success(emptyList()))
                } else {
                    val listMood = ArrayList<Mood>()

                    for (document in snapshot.documents) {
                        document.toObject(Mood::class.java)?.let { listMood.add(it) }
                    }

                    result.invoke(UiState.Success(listMood))
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