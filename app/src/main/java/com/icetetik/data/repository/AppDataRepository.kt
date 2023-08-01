package com.icetetik.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.icetetik.util.KeyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppDataRepository @Inject constructor(private val dataStore: DataStore<Preferences>){

    fun getThemeSetting(): Flow<String?> {
        return dataStore.data.map { preferences ->
            preferences[KeyPreferences.THEME_KEY]
        }
    }

    suspend fun saveThemeSetting(themeId: Int){
        dataStore.edit { preferences ->
            preferences[KeyPreferences.THEME_KEY] = themeId.toString()
        }
    }
}