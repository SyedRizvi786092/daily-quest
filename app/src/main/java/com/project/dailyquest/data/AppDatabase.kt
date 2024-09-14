package com.project.dailyquest.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.project.dailyquest.model.Goal
import com.project.dailyquest.utils.UuidConverter

@Database(entities = [Goal::class], version = 1, exportSchema = false)
@TypeConverters(UuidConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun goalDao(): GoalDao
}