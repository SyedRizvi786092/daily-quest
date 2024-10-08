package com.project.dailyquest.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.userProfileChangeRequest
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

private const val TAG = "FB"

class UserRepository @Inject constructor(private val auth: FirebaseAuth) {

    suspend fun refreshUserData() {
        return suspendCancellableCoroutine { continuation ->
            val user = auth.currentUser
            user!!.reload().addOnCompleteListener { task ->
                if (task.isSuccessful) continuation.resume(Unit)
                else {
                    Log.d(TAG, "refreshUserData: ${task.exception}")
                    continuation.resumeWithException(task.exception?: Exception("Unknown Error"))
                }
            }
        }
    }

    fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                    Log.d(TAG, "signIn: ${task.result}")
                }
                else {
                    onError(Exception(task.exception))
                    // ${task.result} throws an exception that isn't caught
                    Log.d(TAG, "signIn: ${task.exception}")
                }
            }
    }

    fun createNewUser(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { task ->
                Log.d(TAG, "createNewUser: New user created, id: ${task.user?.uid}")
                onSuccess()
            }.addOnFailureListener { exception ->
                Log.d(TAG, "createNewUser: Exception, msg: ${exception.message}")
                onError(exception)
            }
    }

    fun addName(name: String, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val user = auth.currentUser
        val addNewName = userProfileChangeRequest { displayName = name }
        user!!.updateProfile(addNewName)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "addName: displayName added\n ${task.result}")
                    onSuccess()
                }
                else {
                    Log.d(TAG, "addName: ${task.exception}")
                    onError(Exception(task.exception))
                }
            }
    }
}
