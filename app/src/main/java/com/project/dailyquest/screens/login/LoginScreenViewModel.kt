package com.project.dailyquest.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

private const val TAG = "FB"

class LoginScreenViewModel: ViewModel() {
    private val auth = Firebase.auth

    fun signIn(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) onSuccess()
//                        else Log.d(TAG, "signIn: ${task.result}") throws exception that isn't caught
                    }.addOnFailureListener { exception ->
                        Log.d(TAG, "signIn: Exception, msg: ${exception.message}")
                    }
            } catch (exc: Exception) {
                Log.d(TAG, "signIn: Exception, msg: ${exc.message}")
            }
        }
    }

    fun signUp(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnSuccessListener { task ->
                        Log.d(TAG, "signUp: New user created, id: ${task.user?.uid}")
                        onSuccess()
                    }.addOnFailureListener { exception ->
                        Log.d(TAG, "signUp: Exception, msg: ${exception.message}")
                    }
            } catch (exc: Exception) {
                Log.d(TAG, "signUp: Exception, msg: ${exc.message}")
            }
        }
    }
}
