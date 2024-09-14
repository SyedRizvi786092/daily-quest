package com.project.dailyquest.screens.goals

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.dailyquest.model.Goal
import com.project.dailyquest.repository.GoalsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalsViewModel @Inject constructor(private val repository: GoalsRepository): ViewModel() {
    private val _state = MutableStateFlow(Goal.getDummyGoals())
    val state: StateFlow<List<Goal>> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAllGoals().collect { _state.value = it }
        }
    }

    fun deleteGoal(goal: Goal) = viewModelScope.launch(Dispatchers.IO) { repository.deleteGoal(goal) }
    fun addGoal(goal: Goal) = viewModelScope.launch(Dispatchers.IO) { repository.insertGoal(goal) }
    fun editGoal(goal: Goal) = viewModelScope.launch(Dispatchers.IO) { repository.updateGoal(goal) }
}
