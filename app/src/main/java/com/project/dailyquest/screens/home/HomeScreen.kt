package com.project.dailyquest.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.dailyquest.data.getDummyGoals
import com.project.dailyquest.widgets.Dashboard

@Preview(showBackground = true)
@Composable
fun HomeScreen(
    scaffoldPadding: PaddingValues = PaddingValues(),
    goalCount: Int = getDummyGoals().count()
) {
    Column(modifier = Modifier
        .padding(scaffoldPadding)
        .padding(16.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Hello, User!",
            modifier = Modifier.padding(16.dp),
            style = LocalTextStyle.current.copy(fontSize = 40.sp,
                fontFamily = FontFamily.Serif)
        )
        Dashboard(goalCount = goalCount)
    }
}