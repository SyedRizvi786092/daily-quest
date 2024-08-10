package com.project.dailyquest.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.project.dailyquest.screens.GoalsScreen
import com.project.dailyquest.screens.GoalsViewModel
import com.project.dailyquest.screens.HomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    var viewGoals by remember {
        mutableStateOf(false)
    }
    var addGoals by remember {
        mutableStateOf(false)
    }

    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.name) {

        composable(route = AppScreens.HomeScreen.name) {
            HomeScreen(
                onNavigateToGoals = { navController.navigate(route = AppScreens.GoalsScreen.name) }
            )
        }

        composable(route = AppScreens.GoalsScreen.name) {
            val viewModel = hiltViewModel<GoalsViewModel>()
            val goals = viewModel.state.collectAsStateWithLifecycle().value
            GoalsScreen(
                viewGoals = viewGoals,
                onToggleView = { viewGoals = !viewGoals },
                addGoals = addGoals,
                onToggleAdd = { addGoals = !addGoals },
                goals = goals,
                onDeleteGoal = { viewModel.deleteGoal(it) },
                onAddGoal = { viewModel.addGoal(it) },
                onEditGoal = { viewModel.editGoal(it) },
                onNavigateToHome = { navController.navigate(route = AppScreens.HomeScreen.name) }
            )
        }
    }
}