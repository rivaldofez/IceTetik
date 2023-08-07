package com.icetetik.page.authentication.reset

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.icetetik.data.repository.AuthRepository
import com.icetetik.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {

    private val _resetPasswordUser = MutableLiveData<UiState<String>>()
    val resetPasswordUser: LiveData<UiState<String>>
        get() = _resetPasswordUser


    fun resetPasswordUser(email: String){
        _resetPasswordUser.value = UiState.Loading
        authRepository.resetPasswordUser(email) {
            _resetPasswordUser.value = it
        }
    }

    fun getUserSession(result: (String?) -> Unit) {
        authRepository.getUserSession(result)
    }


}