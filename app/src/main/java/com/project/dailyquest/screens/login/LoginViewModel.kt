package com.project.dailyquest.screens.login

import android.util.Log
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
class LoginViewModel @Inject constructor(private val repository: UserRepository): ViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    fun login(email: String, password: String, onSuccess: () -> Unit) {
        _authState.value = AuthState.loading(msg = "Letting you in!")
        viewModelScope.launch {
            try {
                repository.signIn(
                    email = email,
                    password = password,
                    onSuccess = {
                        _authState.value = AuthState.success(msg = "Welcome Back!")
                        onSuccess()
                    },
                    onError = { _authState.value = AuthState.error(
                        exception = it,
                        msg = "Something went wrong!"
                    ) }
                )
            } catch (exc: Exception) {
                _authState.value = AuthState.error(exception = exc, msg = "Something went wrong!")
                Log.d("VM", "login: Exception, msg: ${exc.message}")
            }
        }
    }

    fun signUp(email: String, password: String, onSuccess: () -> Unit) {
        _authState.value = AuthState.loading(msg = "Please wait!")
        viewModelScope.launch {
            try {
                repository.createNewUser(
                    email = email,
                    password = password,
                    onSuccess = {
                        _authState.value = AuthState.success(msg = "Welcome onboard!")
                        onSuccess()
                    },
                    onError = { _authState.value = AuthState.error(
                        exception = it,
                        msg = "Something went wrong!"
                    ) }
                )
            } catch (exc: Exception) {
                _authState.value = AuthState.error(exception = exc, msg = "Something went wrong!")
                Log.d("VM", "signUp: Exception, msg: ${exc.message}")
            }
        }
    }
}
