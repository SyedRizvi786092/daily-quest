package com.project.dailyquest.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseUser
import com.project.dailyquest.screens.SplashScreen
import com.project.dailyquest.screens.goals.GoalsScreen
import com.project.dailyquest.screens.goals.GoalsViewModel
import com.project.dailyquest.screens.home.HomeScreen
import com.project.dailyquest.screens.home.MainViewModel
import com.project.dailyquest.screens.login.LoginScreen
import com.project.dailyquest.screens.login.LoginViewModel
import com.project.dailyquest.screens.profile.ProfileScreen
import com.project.dailyquest.screens.profile.ProfileViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    scaffoldPadding: PaddingValues,
    user: FirebaseUser?
) {
    var viewGoals by remember { mutableStateOf(false) }
    var addGoals by remember { mutableStateOf(false) }
    val mainViewModel = hiltViewModel<MainViewModel>()
    LaunchedEffect(key1 = true) { if (user != null) mainViewModel.refreshUserData() }
    val goalCount = mainViewModel.goalCount.collectAsStateWithLifecycle()
    val authState = mainViewModel.authState.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = AppScreens.SplashScreen.name) {

        composable(route = AppScreens.SplashScreen.name) {
            val route = if (user != null) AppScreens.HomeScreen.name else AppScreens.LoginScreen.name
            var hasNavigated by remember { mutableStateOf(false) }
            SplashScreen(
                userDataRefreshState = authState.value,
                onSplashScreenFinish = {
                    if (!hasNavigated) {
                        hasNavigated = true
                        navController.navigate(route) {
                            popUpTo(AppScreens.SplashScreen.name) { inclusive = true }
                        }
                    }
                }
            )
        }

        composable(route = AppScreens.LoginScreen.name) {
            val loginViewModel = hiltViewModel<LoginViewModel>()
            val loadingState = loginViewModel.authState.collectAsStateWithLifecycle()
            LoginScreen(
                loginLoadingState = loadingState.value,
                signIn = { email, password ->
                    loginViewModel.login(email, password,
                        onSuccess = { navController.navigate(AppScreens.HomeScreen.name) {
                            popUpTo(AppScreens.LoginScreen.name) { inclusive = true }
                        } }
                    )
                },
                signUp = { email, password ->
                    loginViewModel.signUp(email, password,
                        onSuccess = { navController.navigate(AppScreens.ProfileScreen.name) {
                            popUpTo(AppScreens.LoginScreen.name) { inclusive = true }
                        } }
                    )
                }
            )
        }
        
        composable(route = AppScreens.ProfileScreen.name) {
            val profileViewModel = hiltViewModel<ProfileViewModel>()
            val loadingState = profileViewModel.authState.collectAsStateWithLifecycle()
            ProfileScreen(
                scaffoldPadding = scaffoldPadding,
                profileEditLoadingState = loadingState.value,
                user = user!!,
                onAddName = { newName ->
                    profileViewModel.addNewName(
                        name = newName,
                        onSuccess = { navController.navigate(AppScreens.HomeScreen.name) {
                            popUpTo(AppScreens.ProfileScreen.name) { inclusive = true }
                            launchSingleTop = true
                        } }
                    )
                },
                onSkip = { navController.navigate(AppScreens.HomeScreen.name) {
                    popUpTo(AppScreens.ProfileScreen.name) { inclusive = true }
                    launchSingleTop = true
                } }
            )
        }

        composable(route = AppScreens.HomeScreen.name) {
            HomeScreen(
                scaffoldPadding = scaffoldPadding,
                user = user!!,
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
