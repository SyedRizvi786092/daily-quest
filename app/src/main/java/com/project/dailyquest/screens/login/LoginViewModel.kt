package com.project.dailyquest.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.project.dailyquest.model.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "FB"

@HiltViewModel
class LoginViewModel @Inject constructor(private val auth: FirebaseAuth): ViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    fun signIn(email: String, password: String, onSuccess: () -> Unit) {
        _authState.value = AuthState(status = AuthState.Status.LOADING, msg = "Letting you in!")
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            _authState.value = AuthState(AuthState.Status.COMPLETE, "Welcome Back!")
                            onSuccess()
                        }
                        else {
                            _authState.value = AuthState()
                            // ${task.result} throws an exception that isn't caught
                            Log.d(TAG, "signIn: ${task.exception}")
                        }
                    }
            } catch (exc: Exception) {
                _authState.value = AuthState()
                Log.d(TAG, "signIn: Exception, msg: ${exc.message}")
            }
        }
    }

    fun signUp(email: String, password: String, onSuccess: () -> Unit) {
        _authState.value = AuthState(status = AuthState.Status.LOADING, msg = "Please wait!")
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener { task ->
                        _authState.value = AuthState(AuthState.Status.COMPLETE, "Welcome!")
                        Log.d(TAG, "signUp: New user created, id: ${task.user?.uid}")
                        onSuccess()
                    }.addOnFailureListener { exception ->
                        _authState.value = AuthState()
                        Log.d(TAG, "signUp: Exception, msg: ${exception.message}")
                    }
            } catch (exc: Exception) {
                _authState.value = AuthState()
                Log.d(TAG, "signUp: Exception, msg: ${exc.message}")
            }
        }
    }
}
