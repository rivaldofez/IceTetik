package com.icetetik.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.icetetik.data.repository.AuthRepository
import com.icetetik.data.repository.MoodRepository
import com.icetetik.data.repository.QuestionnaireRepository
import com.icetetik.data.repository.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        database: FirebaseFirestore,
        auth: FirebaseAuth
    ): AuthRepository {
        return AuthRepository(auth, database)
    }

    @Provides
    @Singleton
    fun provideQuestionnaireRepository(
        database: FirebaseFirestore,
    ): QuestionnaireRepository {
        return QuestionnaireRepository(database)
    }

    @Provides
    @Singleton
    fun provideMoodRepository(
        database: FirebaseFirestore,
    ): MoodRepository {
        return MoodRepository(database)
    }

    @Provides
    @Singleton
    fun provideVideoRepository(
        database: FirebaseFirestore,
    ): VideoRepository {
        return VideoRepository(database)
    }

}