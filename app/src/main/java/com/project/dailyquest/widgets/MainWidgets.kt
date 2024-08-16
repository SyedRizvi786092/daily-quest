package com.project.dailyquest.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.project.dailyquest.navigation.AppScreens
import com.project.dailyquest.navigation.NavBarItem

@Preview(showBackground = true)
@Composable
fun DashboardContent(
    goalCount: Int = 3,
    pendingTasks: Int = 2,
    avgSleep: Double = 7.4
) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column {
            DisplayMainText(key = "Currently Active Goals: ",
                value = "$goalCount",
                fontSize = 20.sp)
            HorizontalDivider(thickness = 4.dp,
                color = MaterialTheme.colorScheme.surface)
            DisplayMainText(key = "Today's Pending Tasks: ",
                value = "$pendingTasks",
                fontSize = 20.sp)
            HorizontalDivider(thickness = 4.dp,
                color = MaterialTheme.colorScheme.surface)
            DisplayMainText(key = "Average sleep/day: ",
                value = "$avgSleep hrs",
                fontSize = 20.sp)
        }
    }
}

@Composable
fun DisplayMainText(key: String, value: String, fontSize: TextUnit) {
    val text = buildAnnotatedString {
        withStyle(style = SpanStyle(fontWeight = FontWeight.Light)) {
            append(key)
        }
        withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
            append(value)
        }
    }
    Text(text = text,
        modifier = Modifier.padding(4.dp),
        style = LocalTextStyle.current.copy(fontSize = fontSize))
}

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
                onClick = { onNavigateToScreen(it.route) },
                icon = { Icon(
                    painter = painterResource(
                        id = if (isSelected) it.selectedIcon else it.unselectedIcon),
                    contentDescription = "Navigation Icon"
                ) },
                enabled = it.route != null,
                label = { Text(
                    text = it.text,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                ) },
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
    navController: NavHostController
) {
    val topAppBarColor = when(currentScreen) {
        AppScreens.HomeScreen -> MaterialTheme.colorScheme.tertiaryContainer
        AppScreens.GoalsScreen -> MaterialTheme.colorScheme.primaryContainer
        else -> MaterialTheme.colorScheme.background
    }
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
        modifier = modifier,
        navigationIcon = { if (currentScreen != AppScreens.HomeScreen)
            IconButton(onClick = { navController.popBackStack() }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        } },
        actions = { if (currentScreen != AppScreens.HomeScreen)
            IconButton(onClick = { navController.navigate(AppScreens.HomeScreen.name) {
                popUpTo(AppScreens.HomeScreen.name) { inclusive = true } } }) {
                Icon(imageVector = Icons.Default.Home, contentDescription = "Home")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = topAppBarColor,
            titleContentColor = contentColorFor(backgroundColor = topAppBarColor)
        )
    )
}