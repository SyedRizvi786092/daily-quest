package com.project.dailyquest.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.project.dailyquest.utils.getEpochTime
import java.util.UUID

@Entity(tableName = "goals")
data class Goal(
    @PrimaryKey val id: UUID,
    @ColumnInfo(name = "goal_title") val title: String,
    @ColumnInfo(name = "goal_description") val description: String?,
    @ColumnInfo(name = "goal_deadline") val deadline: Long?
)

fun getDummyGoals(): List<Goal> {
    return listOf(

        Goal(
            id = UUID.randomUUID(),
            title = "Learn DSA",
            description = "Data Structures & Algorithms is a key concept which is crucial for " +
                    "Interview Preparation.",
            deadline = getEpochTime(date = "2024-11-30")
        ),

        Goal(
            id = UUID.randomUUID(),
            title = "Dive deep into Android Development",
            description = "Mastering DSA is not enough in today's competitive world. Android " +
                    "Development is your passion, so follow that.",
            deadline = getEpochTime(date = "2025-01-31")
        ),

        Goal(
            id = UUID.randomUUID(),
            title = "Gain Spirituality",
            description = "Remember that you are a follower of the one who promoted knowledge and" +
                    " education, but at the same time, instructed us to never bargain the " +
                    "eternal life for this perishable world. How can you forget this!",
            deadline = null
        ),

        Goal(
            id = UUID.randomUUID(),
            title = "Do something for the community!",
            description = null,
            deadline = null
        ),

        Goal(
            id = UUID.randomUUID(),
            title = "For Testing Purposes",
            description = null,
            deadline = getEpochTime(date = "2024-10-10")
        )
    )
}