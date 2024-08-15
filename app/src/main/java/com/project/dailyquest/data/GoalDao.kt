package com.project.dailyquest.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface GoalDao {

    @Query("SELECT COUNT(*) FROM goals")
    fun countGoals(): Flow<Int>

    @Query("SELECT * FROM goals")
    fun getAllGoals(): Flow<List<Goal>>

    @Delete
    suspend fun deleteGoal(goal: Goal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGoal(goal: Goal)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGoal(goal: Goal)
}