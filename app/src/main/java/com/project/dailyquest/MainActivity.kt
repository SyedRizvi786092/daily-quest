package com.project.dailyquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.project.dailyquest.navigation.AppNavigation
import com.project.dailyquest.navigation.AppScreens
import com.project.dailyquest.navigation.getAllNavBarItems
import com.project.dailyquest.ui.theme.DailyQuestTheme
import com.project.dailyquest.widgets.BottomNavigationBar
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppContent() {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen by remember(currentBackStackEntry) {
        mutableStateOf(
            when(currentBackStackEntry?.destination?.route) {
                AppScreens.HomeScreen.name -> AppScreens.HomeScreen
                AppScreens.GoalsScreen.name -> AppScreens.GoalsScreen
                else -> AppScreens.SplashScreen
            }
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
        val topAppBarColor = when(currentScreen) {
            AppScreens.HomeScreen -> MaterialTheme.colorScheme.tertiaryContainer
            AppScreens.GoalsScreen -> MaterialTheme.colorScheme.primaryContainer
            else -> MaterialTheme.colorScheme.background
        }
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center) {
                            Text(text = when(currentScreen) {
                                AppScreens.HomeScreen -> "Dashboard"
                                AppScreens.GoalsScreen -> "Goals"
                                else -> ""
                            },
                                style = LocalTextStyle.current.copy(fontWeight = FontWeight.Bold))
                        }
                    },
                    actions = { if (currentScreen != AppScreens.HomeScreen)
                        IconButton(onClick = { navController.navigate(AppScreens.HomeScreen.name) {
                            popUpTo(AppScreens.HomeScreen.name) {
                                inclusive = true
                            } } }) {
                            Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = topAppBarColor,
                        titleContentColor = contentColorFor(backgroundColor = topAppBarColor)
                    )
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DailyQuestTheme {
        AppContent()
    }
}