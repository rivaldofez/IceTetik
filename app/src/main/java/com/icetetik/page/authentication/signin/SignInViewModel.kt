package com.icetetik.page.authentication.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.icetetik.data.model.User
import com.icetetik.data.repository.AuthRepository
import com.icetetik.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val repository: AuthRepository
): ViewModel(){

    private val _signInUser = MutableLiveData<UiState<String>>()
    val signInUser: LiveData<UiState<String>>
        get() = _signInUser

    private val _saveUserInfo = MutableLiveData<UiState<String>>()
    val saveUserInfo: LiveData<UiState<String>>
        get() = _saveUserInfo

    private val _userInfo = MutableLiveData<UiState<User?>>()
    val userInfo: LiveData<UiState<User?>>
        get() = _userInfo


//    fun saveUserInfo(user: User, result: (UiState<String>) -> Unit) {

    fun signIn(email: String, password: String){
        _signInUser.value = UiState.Loading
        repository.signInUser(email = email, password = password){
            _signInUser.value = it
        }
    }

    fun saveUserInfo(user: User){
        _saveUserInfo.value = UiState.Loading
        repository.saveUserInfo(user){
            _saveUserInfo.value = it
        }
    }

    fun getUserInfo(userEmail: String){
        _userInfo.value = UiState.Loading

        repository.getUserInfo(userEmail = userEmail){
            _userInfo.value = it
        }
    }
}