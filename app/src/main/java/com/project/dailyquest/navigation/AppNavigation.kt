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
import com.project.dailyquest.screens.MainViewModel
import com.project.dailyquest.screens.SplashScreen

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String,
    scaffoldPadding: PaddingValues
) {
    var viewGoals by remember {
        mutableStateOf(false)
    }
    var addGoals by remember {
        mutableStateOf(false)
    }
    val mainViewModel = hiltViewModel<MainViewModel>()
    val goalCount = mainViewModel.goalCount.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = startDestination) {

        composable(route = AppScreens.SplashScreen.name) {
            SplashScreen(
                onSplashScreenFinish = { navController.navigate(AppScreens.HomeScreen.name) }
            )
        }

        composable(route = AppScreens.HomeScreen.name) {
            HomeScreen(
                scaffoldPadding = scaffoldPadding,
                goalCount = goalCount.value
            )
        }

        composable(route = AppScreens.GoalsScreen.name) {
            val goalsViewModel = hiltViewModel<GoalsViewModel>()
            val goals = goalsViewModel.state.collectAsStateWithLifecycle()
            GoalsScreen(
                scaffoldPadding = scaffoldPadding,
                goalCount = goalCount.value,
                viewGoals = viewGoals,
                onToggleView = { viewGoals = !viewGoals },
                addGoals = addGoals,
                onToggleAdd = { addGoals = !addGoals },
                goals = goals.value,
                onDeleteGoal = { goalsViewModel.deleteGoal(it) },
                onAddGoal = { goalsViewModel.addGoal(it) },
                onEditGoal = { goalsViewModel.editGoal(it) }
            )
        }
    }
}