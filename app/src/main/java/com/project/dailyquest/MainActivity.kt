package com.project.dailyquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.project.dailyquest.components.BottomNavigationBar
import com.project.dailyquest.components.TopApplicationBar
import com.project.dailyquest.navigation.AppNavigation
import com.project.dailyquest.navigation.AppScreens
import com.project.dailyquest.navigation.NavBarItem
import com.project.dailyquest.ui.theme.DailyQuestTheme
import com.project.dailyquest.widgets.ConfirmationDialog
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyQuestTheme {
                AppContent(auth = auth)
            }
        }
    }
}

@Composable
fun AppContent(auth: FirebaseAuth) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen by remember(currentBackStackEntry) {
        mutableStateOf(
            AppScreens.fromRoute(currentBackStackEntry?.destination?.route)
                ?: AppScreens.SplashScreen
        )
    }
    var showLogoutDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { when(currentScreen) {
            AppScreens.SplashScreen, AppScreens.LoginScreen -> {}
            else -> { TopApplicationBar(
                currentScreen = currentScreen,
                navController = navController,
                userName = auth.currentUser?.displayName,
                logOut = { showLogoutDialog = true }
            ) }
        } },
        bottomBar = { when(currentScreen) {
            AppScreens.SplashScreen, AppScreens.LoginScreen -> {}
            else -> { BottomNavigationBar(
                items = NavBarItem.getAllNavBarItems(),
                currentScreen = currentScreen,
                onNavigateToScreen = { screenRoute ->
                    if (screenRoute != null) navController.navigate(screenRoute)
                }) }
        } }
    ) { innerPadding ->
        AppNavigation(
            navController = navController,
            scaffoldPadding = innerPadding,
            user = auth.currentUser
        )
    }

    if (showLogoutDialog)
        ConfirmationDialog(
            action = "Logout",
            msg = "Are you sure you want to logout?",
            onConfirm = {
                auth.signOut()
                showLogoutDialog = false
                navController.navigate(AppScreens.LoginScreen.name) {
                    popUpTo(AppScreens.HomeScreen.name) { inclusive = true }
                }
            },
            onDismiss = { showLogoutDialog = false }
        )
}
