package com.project.dailyquest.data

import com.project.dailyquest.utils.getEpochTime
import java.util.UUID

data class Goal(
    val id: UUID,
    val name: String,
    val description: String,
    val deadline: Long?
)

fun getDummyGoals(): List<Goal> {
    return listOf(

        Goal(
            id = UUID.randomUUID(),
            name = "Learn DSA",
            description = "Data Structures & Algorithms is a key concept which is crucial for " +
                    "Interview Preparation.",
            deadline = getEpochTime(date = "2024-11-30")
        ),

        Goal(
            id = UUID.randomUUID(),
            name = "Dive deep into Android Development",
            description = "Mastering DSA is not enough in today's competitive world. Android " +
                    "Development is your passion, so follow that.",
            deadline = getEpochTime(date = "2025-01-31")
        ),

        Goal(
            id = UUID.randomUUID(),
            name = "Gain Spirituality",
            description = "Remember that you are a follower of the one who promoted knowledge and" +
                    " education, but at the same time, instructed us to never leave the religion " +
                    "for this perishable world. How can you forget this!",
            deadline = null
        )
    )
}