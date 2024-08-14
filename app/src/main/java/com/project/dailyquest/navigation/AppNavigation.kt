package com.project.dailyquest.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.project.dailyquest.screens.GoalsScreen
import com.project.dailyquest.screens.GoalsViewModel
import com.project.dailyquest.screens.HomeScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    scaffoldPadding: PaddingValues
) {
    var viewGoals by remember {
        mutableStateOf(false)
    }
    var addGoals by remember {
        mutableStateOf(false)
    }

    NavHost(navController = navController, startDestination = AppScreens.HomeScreen.name) {

        composable(route = AppScreens.HomeScreen.name) {
            HomeScreen(
                scaffoldPadding = scaffoldPadding,
                onNavigateToGoals = { navController.navigate(AppScreens.GoalsScreen.name) }
            )
        }

        composable(route = AppScreens.GoalsScreen.name) {
            val viewModel = hiltViewModel<GoalsViewModel>()
            val goals = viewModel.state.collectAsStateWithLifecycle().value
            GoalsScreen(
                scaffoldPadding = scaffoldPadding,
                viewGoals = viewGoals,
                onToggleView = { viewGoals = !viewGoals },
                addGoals = addGoals,
                onToggleAdd = { addGoals = !addGoals },
                goals = goals,
                onDeleteGoal = { viewModel.deleteGoal(it) },
                onAddGoal = { viewModel.addGoal(it) },
                onEditGoal = { viewModel.editGoal(it) }
            )
        }
    }
}