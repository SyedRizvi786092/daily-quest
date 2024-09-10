package com.project.dailyquest.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.project.dailyquest.R
import com.project.dailyquest.navigation.AppScreens
import com.project.dailyquest.navigation.NavBarItem

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    items: List<NavBarItem>,
    currentScreen: AppScreens,
    onNavigateToScreen: (String?) -> Unit
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.tertiaryContainer,
        tonalElevation = 4.dp
    ) {
        items.forEach {
            val isSelected = AppScreens.fromRoute(it.route) == currentScreen
            NavigationBarItem(
                selected = isSelected,
                onClick = { if (!isSelected) onNavigateToScreen(it.route) },
                icon = {
                    Icon(
                        painter = painterResource(
                            id = if (isSelected) it.selectedIcon else it.unselectedIcon
                        ),
                        contentDescription = "Navigation Icon"
                    )
                },
                enabled = it.route != null,
                label = {
                    Text(
                        text = it.text,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onTertiary,
                    indicatorColor = MaterialTheme.colorScheme.tertiary,
                    unselectedIconColor = MaterialTheme.colorScheme.onTertiaryContainer,
                    unselectedTextColor = MaterialTheme.colorScheme.onTertiaryContainer
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopApplicationBar(
    modifier: Modifier = Modifier,
    currentScreen: AppScreens,
    navController: NavHostController,
    logOut: () -> Unit
) {
    val topAppBarColor = when(currentScreen) {
        AppScreens.HomeScreen -> MaterialTheme.colorScheme.tertiaryContainer
        AppScreens.GoalsScreen -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.background
    }
    TopAppBar(
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = when (currentScreen) {
                        AppScreens.HomeScreen -> "Dashboard"
                        AppScreens.GoalsScreen -> "Goals"
                        else -> ""
                    },
                    style = LocalTextStyle.current.copy(fontWeight = FontWeight.Bold)
                )
            }
        },
        modifier = modifier,
        navigationIcon = {
            if (currentScreen != AppScreens.HomeScreen)
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
        },
        actions = {
            if (currentScreen != AppScreens.HomeScreen)
                IconButton(onClick = {
                    navController.navigate(AppScreens.HomeScreen.name) {
                        popUpTo(AppScreens.HomeScreen.name) { inclusive = true }
                    }
                }) {
                    Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
                }
            else
                IconButton(onClick = logOut) {
                    Icon(
                        painter = painterResource(id = R.drawable.logout_24dp),
                        contentDescription = "Logout"
                    )
                }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = topAppBarColor,
            titleContentColor = contentColorFor(backgroundColor = topAppBarColor)
        )
    )
}
