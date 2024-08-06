package com.project.dailyquest.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.dailyquest.data.Goal
import com.project.dailyquest.utils.getRemainingDays
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus

@Composable
fun ShowGoal(goal: Goal) {
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

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    imeAction: ImeAction = ImeAction.Default,
    onImeAction: () -> Unit = {},
    maxLines: Int = 1,
    color: Color = MaterialTheme.colorScheme.surfaceContainer
) {
    TextField(value = text, onValueChange = onTextChange,
        modifier = modifier.padding(5.dp),
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        keyboardActions = KeyboardActions { onImeAction() },
        maxLines = maxLines,
        colors = TextFieldDefaults.colors().copy(focusedContainerColor = color,
            unfocusedContainerColor = color)
    )
}

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDatePicker(
    onDateSelected: (Long?) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val tomorrow = Clock.System.now().plus(48, DateTimeUnit.HOUR, TimeZone.currentSystemDefault())
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = tomorrow.toEpochMilliseconds()
    )
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState,
            title = { Text(text = "Set Deadline", modifier = Modifier.padding(16.dp)) })
    }
}