package com.icetetik.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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


    fun getUserInfo(userEmail: String){
        _userInfo.value = UiState.Loading

        authRepository.getUserInfo(userEmail = userEmail){
            _userInfo.value = it
        }
    }


}