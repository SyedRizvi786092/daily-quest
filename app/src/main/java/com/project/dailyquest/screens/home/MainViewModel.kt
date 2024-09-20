package com.project.dailyquest.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.dailyquest.model.AuthState
import com.project.dailyquest.model.Goal
import com.project.dailyquest.repository.GoalsRepository
import com.project.dailyquest.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val goalsRepository: GoalsRepository
): ViewModel() {

    private val _authState = MutableStateFlow(AuthState())
    val authState = _authState.asStateFlow()

    private val _goalCount = MutableStateFlow(Goal.getDummyGoals().count())
    val goalCount = _goalCount.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            goalsRepository.countGoals().distinctUntilChanged().collect { _goalCount.value = it }
        }
    }

    fun refreshUserData() {
        _authState.value = AuthState.loading()
        viewModelScope.launch(Dispatchers.IO) {
            try {
                userRepository.refreshUserData()
                _authState.value = AuthState.success()
            } catch (exc: Exception) {
                Log.d("FB", "Exception while loading user data: ${exc.message}")
                _authState.value = AuthState.error(
                    exception = exc,
                    msg = "Unable to fetch latest data! Please check your internet connection."
                )
            }
        }
    }
}
