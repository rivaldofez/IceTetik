package com.icetetik.launcher

import androidx.lifecycle.ViewModel
import com.icetetik.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LauncherViewModel @Inject constructor(
    val repository: AuthRepository
): ViewModel(){


    fun getUserSession(result: (String?) -> Unit) {
        repository.getUserSession(result)
    }

}