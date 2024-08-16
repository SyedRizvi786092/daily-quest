package com.project.dailyquest.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
            NavigationBarItem(
                selected = AppScreens.fromRoute(it.route) == currentScreen,
                onClick = { onNavigateToScreen(it.route) },
                icon = { Icon(
                    painter = painterResource(id = it.icon),
                    contentDescription = "Navigation Icon"
                ) },
                label = { Text(text = it.text) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    }
}