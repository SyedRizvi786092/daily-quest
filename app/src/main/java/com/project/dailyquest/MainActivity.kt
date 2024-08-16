package com.project.dailyquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.dailyquest.navigation.AppNavigation
import com.project.dailyquest.navigation.AppScreens
import com.project.dailyquest.navigation.getAllNavBarItems
import com.project.dailyquest.ui.theme.DailyQuestTheme
import com.project.dailyquest.widgets.BottomNavigationBar
import com.project.dailyquest.widgets.TopApplicationBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyQuestTheme {
                AppContent()
            }
        }
    }
}

@Composable
fun AppContent() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen by remember(currentBackStackEntry) {
        mutableStateOf(
            AppScreens.fromRoute(currentBackStackEntry?.destination?.route)
                ?: AppScreens.SplashScreen
        )
    }

    if (currentScreen == AppScreens.SplashScreen) {
        AppNavigation(
            navController = navController,
            startDestination = AppScreens.SplashScreen.name,
            scaffoldPadding = PaddingValues()
        )
    }
    else {
        Scaffold(
            topBar = {
                TopApplicationBar(
                    currentScreen = currentScreen,
                    navController = navController
                )
            },
            bottomBar = { BottomNavigationBar(
                items = getAllNavBarItems(),
                currentScreen = currentScreen,
                onNavigateToScreen = { screenRoute ->
                    if (screenRoute != null) {
                        navController.navigate(screenRoute)
                    }
                }
            ) }
        ) { innerPadding ->
            AppNavigation(
                navController = navController,
                startDestination = AppScreens.HomeScreen.name,
                scaffoldPadding = innerPadding
            )
        }
    }
}