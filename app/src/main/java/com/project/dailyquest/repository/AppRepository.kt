package com.project.dailyquest.repository

import com.project.dailyquest.data.Goal
import com.project.dailyquest.data.GoalDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AppRepository @Inject constructor(private val goalDao: GoalDao) {

    fun getAllGoals(): Flow<List<Goal>> {
        return goalDao.getAllGoals().flowOn(Dispatchers.IO).conflate()
    }
    suspend fun deleteGoal(goal: Goal) = goalDao.deleteGoal(goal)
    suspend fun insertGoal(goal: Goal) = goalDao.insertGoal(goal)
    suspend fun updateGoal(goal: Goal) = goalDao.updateGoal(goal)
}