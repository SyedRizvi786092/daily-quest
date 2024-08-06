package com.project.dailyquest.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.dailyquest.screens.GoalsScreen
import com.project.dailyquest.screens.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.name) {

        composable(route = AppScreens.HomeScreen.name) {
            HomeScreen {
                navController.navigate(route = AppScreens.GoalsScreen.name)
            }
        }

        composable(route = AppScreens.GoalsScreen.name) {
            GoalsScreen {
                navController.navigate(route = AppScreens.HomeScreen.name)
            }
        }
    }
}