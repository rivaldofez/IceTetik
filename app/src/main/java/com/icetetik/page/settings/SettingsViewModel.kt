package com.icetetik.page.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icetetik.data.model.User
import com.icetetik.data.repository.AppDataRepository
import com.icetetik.data.repository.AuthRepository
import com.icetetik.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val appDataRepository: AppDataRepository
): ViewModel() {

    private val _userInfo = MutableLiveData<UiState<User?>>()
    val userInfo: LiveData<UiState<User?>>
        get() = _userInfo

    private val _signOut = MutableLiveData<UiState<String>>()
    val signOut: LiveData<UiState<String>>
        get() = _signOut

    private val _deleteAccount = MutableLiveData<UiState<String>>()
    val deleteAccount: LiveData<UiState<String>>
        get() = _deleteAccount


    fun deleteAccountUser() {
        _deleteAccount.value = UiState.Loading
        authRepository.deleteAccount {
            _deleteAccount.value = it
        }
    }

    fun signOutUser(){
        _signOut.value = UiState.Loading
        authRepository.signOutUser {
            _signOut.value = it
        }
    }

    fun getUserInfo(userEmail: String){
        _userInfo.value = UiState.Loading

        authRepository.getUserInfo(userEmail = userEmail){
            _userInfo.value = it
        }
    }

    fun getUserSession(result: (String?) -> Unit) {
        authRepository.getUserSession(result)
    }

    fun getThemeSetting(): Flow<String?> = appDataRepository.getThemeSetting()

    fun saveThemeSetting(themeId: Int) {
        viewModelScope.launch {
            appDataRepository.saveThemeSetting(themeId = themeId)
        }
    }
}