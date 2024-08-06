package com.project.dailyquest.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.dailyquest.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    title: String,
    topAppBarColor: Color,
    homeButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center) {
                        Text(text = title,
                            style = LocalTextStyle.current.copy(fontWeight = FontWeight.Bold))
                    }
                },
                actions = { homeButton() },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = topAppBarColor,
                    titleContentColor = contentColorFor(backgroundColor = topAppBarColor))
            )
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}

@Composable
private fun NavigationButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Surface(onClick = onClick,
        modifier = modifier
            .padding(5.dp)
            .height(100.dp),
        enabled = enabled,
        shape = CutCornerShape(20.dp),
        color = if (enabled) MaterialTheme.colorScheme.surfaceTint
        else MaterialTheme.colorScheme.tertiaryContainer,
        tonalElevation = 4.dp,
        shadowElevation = 4.dp) {
        Column(modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Icon(painter = painterResource(id = icon),
                contentDescription = "Button Icon",
                modifier = Modifier.size(40.dp))
            Text(text = text,
                modifier = Modifier.padding(top = 2.dp),
                style = LocalTextStyle.current.copy(fontSize = 10.sp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardContent(
    goalsCount: Int = 3,
    pendingTasks: Int = 2,
    avgSleep: Double = 7.4
) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp),
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Column {
            DisplayMainText(key = "Currently Active Goals: ",
                value = "$goalsCount",
                fontSize = 20.sp)
            HorizontalDivider(thickness = 5.dp,
                color = MaterialTheme.colorScheme.surface)
            DisplayMainText(key = "Today's Pending Tasks: ",
                value = "$pendingTasks",
                fontSize = 20.sp)
            HorizontalDivider(thickness = 5.dp,
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
        modifier = Modifier.padding(5.dp),
        style = LocalTextStyle.current.copy(fontSize = fontSize))
}

@Composable
fun NavigationBar(
    disabledButton: String? = null,
    onClickGoals: () -> Unit = {},
    onClickTasks: () -> Unit = {},
    onClickSleep: () -> Unit = {}
) {
    LazyVerticalGrid(columns = GridCells.Fixed(3),
        modifier = Modifier.fillMaxHeight(),
        reverseLayout = true) {
        item {
            NavigationButton(text = "Configure Goals",
                icon = R.drawable.target,
                enabled = !(disabledButton.equals("Configure Goals"))) { onClickGoals() }
        }
        item {
            NavigationButton(text = "Today's Tasks",
                icon = R.drawable.task,
                enabled = !(disabledButton.equals("Today's Tasks"))) { onClickTasks() }
        }
        item {
            NavigationButton(text = "Monitor Sleep",
                icon = R.drawable.sleep,
                enabled = !(disabledButton.equals("Monitor Sleep"))) { onClickSleep() }
        }
    }
}