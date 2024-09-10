package com.project.dailyquest.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseUser
import com.project.dailyquest.screens.goals.GoalsScreen
import com.project.dailyquest.screens.goals.GoalsViewModel
import com.project.dailyquest.screens.home.HomeScreen
import com.project.dailyquest.screens.home.MainViewModel
import com.project.dailyquest.screens.SplashScreen
import com.project.dailyquest.screens.login.LoginScreen
import com.project.dailyquest.screens.login.LoginScreenViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    scaffoldPadding: PaddingValues,
    user: FirebaseUser?
) {
    var viewGoals by remember {
        mutableStateOf(false)
    }
    var addGoals by remember {
        mutableStateOf(false)
    }
    val mainViewModel = hiltViewModel<MainViewModel>()
    val goalCount = mainViewModel.goalCount.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.name) {

        composable(route = AppScreens.SplashScreen.name) {
            val route = if (user != null) AppScreens.HomeScreen.name else AppScreens.LoginScreen.name
            SplashScreen(
                onSplashScreenFinish = { navController.navigate(route) {
                    popUpTo(AppScreens.SplashScreen.name) { inclusive = true }
                } }
            )
        }

        composable(route = AppScreens.LoginScreen.name) {
            val loginViewModel = viewModel<LoginScreenViewModel>()
            LoginScreen(
                signIn = { email, password ->
                    loginViewModel.signIn(email, password,
                        onSuccess = { navController.navigate(AppScreens.HomeScreen.name) {
                            popUpTo(AppScreens.LoginScreen.name) { inclusive = true }
                        } }
                    )
                },
                signUp = { email, password ->
                    loginViewModel.signUp(email, password,
                        onSuccess = { navController.navigate(AppScreens.HomeScreen.name) {
                            popUpTo(AppScreens.LoginScreen.name) { inclusive = true }
                        } }
                    )
                }
            )
        }

        composable(route = AppScreens.HomeScreen.name) {
            HomeScreen(
                user = user,
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
