package com.icetetik.page.launcher

import androidx.lifecycle.ViewModel
import com.icetetik.data.repository.AppDataRepository
import com.icetetik.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor(
    val authRepository: AuthRepository,
    val appDataRepository: AppDataRepository

): ViewModel(){
    fun getThemeSetting(): Flow<String?> = appDataRepository.getThemeSetting()

    suspend fun saveThemeSetting(themeId: Int) = appDataRepository.saveThemeSetting(themeId = themeId)

    fun getUserSession(result: (String?) -> Unit) {
        authRepository.getUserSession(result)
    }

}