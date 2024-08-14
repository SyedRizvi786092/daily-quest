package com.project.dailyquest.navigation

import com.project.dailyquest.R

data class NavBarItem(
    val text: String,
    val icon: Int,
    val screen: AppScreens?
)

fun getAllNavBarItems(): List<NavBarItem> {
    return listOf(
        NavBarItem(
            text = "Configure Goals",
            icon = R.drawable.target,
            screen = AppScreens.GoalsScreen
        ),
        NavBarItem(
            text = "Today's Tasks",
            icon = R.drawable.task,
            screen = null
        ),
        NavBarItem(
            text = "Monitor Sleep",
            icon = R.drawable.sleep,
            screen = null
        )
    )
}