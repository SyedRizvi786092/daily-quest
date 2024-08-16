package com.project.dailyquest.navigation

import com.project.dailyquest.R

data class NavBarItem(
    val text: String,
    val icon: Int,
    val route: String?
)

fun getAllNavBarItems(): List<NavBarItem> {
    return listOf(
        NavBarItem(
            text = "Configure Goals",
            icon = R.drawable.target,
            route = AppScreens.GoalsScreen.name
        ),
        NavBarItem(
            text = "Today's Tasks",
            icon = R.drawable.task,
            route = null
        ),
        NavBarItem(
            text = "Monitor Sleep",
            icon = R.drawable.sleep,
            route = null
        )
    )
}