package com.project.dailyquest.screens.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import com.project.dailyquest.model.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val auth: FirebaseAuth): ViewModel() {
    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    fun addNewName(name: String, onSuccess: () -> Unit) {
        _authState.value = AuthState(status = AuthState.Status.LOADING, msg = "Please wait!")
        viewModelScope.launch {
            val user = auth.currentUser
            val addName = userProfileChangeRequest { displayName = name }
            user!!.updateProfile(addName)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _authState.value = AuthState(AuthState.Status.COMPLETE, "Welcome!")
                        onSuccess()
                    } else {
                        Log.d("FB", "addNewName: ${task.exception}")
                    }
                }
        }
    }
}
