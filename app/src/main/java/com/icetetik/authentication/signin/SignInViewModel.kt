package com.icetetik.authentication.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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


    fun signIn(email: String, password: String){
        _signInUser.value = UiState.Loading
        repository.signInUser(email = email, password = password){
            _signInUser.value = it
        }
    }

}