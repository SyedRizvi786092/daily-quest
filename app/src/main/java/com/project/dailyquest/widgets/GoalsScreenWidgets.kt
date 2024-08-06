package com.project.dailyquest.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.dailyquest.data.Goal
import com.project.dailyquest.data.getDummyGoals
import com.project.dailyquest.utils.getRemainingDays

@Preview(showBackground = true)
@Composable
fun ShowGoal(goal: Goal = getDummyGoals()[1]) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        color = MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.3f),
        tonalElevation = 4.dp,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
                }
            }
            Column {
                Text(
                    text = goal.name,
                    style = LocalTextStyle.current.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = goal.description,
                    style = LocalTextStyle.current.copy(fontSize = 12.sp)
                )
                if (goal.deadline != null)
                    Text(
                        text = "(Days Left: ${getRemainingDays(goal.deadline)})",
                        style = LocalTextStyle.current.copy(
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 10.sp
                        )
                    )
            }
        }
    }
}