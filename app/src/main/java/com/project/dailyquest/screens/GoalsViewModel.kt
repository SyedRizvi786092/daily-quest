package com.project.dailyquest.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.dailyquest.data.Goal
import com.project.dailyquest.data.getDummyGoals
import com.project.dailyquest.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(private val repository: AppRepository): ViewModel() {
    private val _state = MutableStateFlow(getDummyGoals())
    val state: StateFlow<List<Goal>> = _state

    private val _goalCount = MutableStateFlow(getDummyGoals().count())
    val goalCount = _goalCount.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllGoals().collect { _state.value = it }
        }
        viewModelScope.launch(Dispatchers.IO) {
            repository.countGoals().distinctUntilChanged().collect { _goalCount.value = it }
        }
    }

    fun deleteGoal(goal: Goal) = viewModelScope.launch { repository.deleteGoal(goal) }
    fun addGoal(goal: Goal) = viewModelScope.launch { repository.insertGoal(goal) }
    fun editGoal(goal: Goal) = viewModelScope.launch { repository.updateGoal(goal) }
}