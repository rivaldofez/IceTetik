package com.icetetik.page.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icetetik.data.model.User
import com.icetetik.data.repository.AuthRepository
import com.icetetik.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _userInfo = MutableLiveData<UiState<User?>>()
    val userInfo: LiveData<UiState<User?>>
        get() = _userInfo

    private val _signOut = MutableLiveData<UiState<String>>()
    val signOut: LiveData<UiState<String>>
        get() = _signOut

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


}