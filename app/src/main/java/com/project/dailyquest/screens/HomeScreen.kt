package com.project.dailyquest.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.dailyquest.widgets.AppScaffold
import com.project.dailyquest.widgets.DashboardContent
import com.project.dailyquest.widgets.NavigationBar

@Preview(showBackground = true)
@Composable
fun HomeScreen(onNavigateToGoals: () -> Unit = {}) {
    AppScaffold(title = "Dashboard",
        topAppBarColor = MaterialTheme.colorScheme.tertiaryContainer) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Hello, User!",
                modifier = Modifier.padding(16.dp),
                style = LocalTextStyle.current.copy(fontSize = 40.sp,
                    fontFamily = FontFamily.Serif)
            )
            DashboardContent()
            NavigationBar(onClickGoals = onNavigateToGoals)
        }
    }
}