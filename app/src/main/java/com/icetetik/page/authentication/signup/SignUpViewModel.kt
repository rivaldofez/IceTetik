package com.icetetik.page.authentication.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.icetetik.data.model.User
import com.icetetik.data.repository.AuthRepository
import com.icetetik.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    val repository: AuthRepository
): ViewModel() {

    private val _signUpUser = MutableLiveData<UiState<String>>()
    val signUpUser: LiveData<UiState<String>> get() = _signUpUser

    fun signUpUser(
        name: String,
        email: String,
        password: String,
    ){
        _signUpUser.value = UiState.Loading
        repository.signUpUser(
            user = User(
                name = name,
                email = email,
                password = password,
                avatar = ""
            )
        ) {
            _signUpUser.value = it
        }

    }

}