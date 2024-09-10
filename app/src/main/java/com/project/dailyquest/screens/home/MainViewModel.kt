package com.project.dailyquest.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.dailyquest.data.getDummyGoals
import com.project.dailyquest.repository.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: AppRepository): ViewModel() {
    private val _goalCount = MutableStateFlow(getDummyGoals().count())
    val goalCount = _goalCount.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.countGoals().distinctUntilChanged().collect { _goalCount.value = it }
        }
    }
}