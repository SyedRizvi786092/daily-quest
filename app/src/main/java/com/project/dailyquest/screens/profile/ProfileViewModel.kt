package com.project.dailyquest.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.dailyquest.model.AuthState
import com.project.dailyquest.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    fun addNewName(name: String, onSuccess: () -> Unit) {
        _authState.value = AuthState(status = AuthState.Status.LOADING, msg = "Please wait!")
        viewModelScope.launch {
            repository.addName(
                name = name,
                onSuccess = {
                    _authState.value = AuthState.success(msg = "Success")
                    onSuccess()
                },
                onError = { _authState.value = AuthState.error(
                    exception = it,
                    msg = "Something went wrong!"
                ) }
            )
        }
    }
}
