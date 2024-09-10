package com.project.dailyquest.navigation

import com.project.dailyquest.R

data class NavBarItem(
    val text: String,
    val selectedIcon: Int,
    val unselectedIcon: Int,
    val route: String?
) {
    companion object {
        fun getAllNavBarItems(): List<NavBarItem> {
            return listOf(
                NavBarItem(
                    text = "Configure Goals",
                    selectedIcon = R.drawable.filled_flag_24,
                    unselectedIcon = R.drawable.outline_flag_24,
                    route = AppScreens.GoalsScreen.name
                ),
                NavBarItem(
                    text = "Today's Tasks",
                    selectedIcon = R.drawable.filled_task_24,
                    unselectedIcon = R.drawable.outline_task_24,
                    route = null
                ),
                NavBarItem(
                    text = "Monitor Sleep",
                    selectedIcon = R.drawable.filled_sleep_24,
                    unselectedIcon = R.drawable.outline_sleep_24,
                    route = null
                )
            )
        }
    }
}