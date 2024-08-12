package com.project.dailyquest.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.project.dailyquest.data.Goal
import com.project.dailyquest.utils.getDecoratedDeadline
import com.project.dailyquest.utils.getRemainingDays
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus

@Composable
fun ShowGoal(
    modifier: Modifier = Modifier,
    goal: Goal,
    onEditGoal: () -> Unit,
    onDeleteGoal: () -> Unit
) {
    Surface(
        modifier = modifier
            .padding(4.dp),
        color = MaterialTheme.colorScheme.surfaceTint.copy(alpha = 0.3f),
        tonalElevation = 4.dp,
        shadowElevation = 2.dp
    ) {
        Row(
            modifier = Modifier
                .padding(2.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onEditGoal) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
            Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                Text(
                    text = goal.title,
                    style = LocalTextStyle.current.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                goal.description?.let {
                    Text(
                        text = it,
                        style = LocalTextStyle.current.copy(fontSize = 12.sp)
                    )
                }
                goal.deadline?.let {
                    Text(
                        text = "(Days Left: ${getRemainingDays(it)})",
                        style = LocalTextStyle.current.copy(
                            color = MaterialTheme.colorScheme.error,
                            fontSize = 8.sp
                        )
                    )
                }
            }
            IconButton(onClick = onDeleteGoal) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
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
        modifier = modifier.padding(4.dp),
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        keyboardActions = KeyboardActions { onImeAction() },
        maxLines = maxLines,
        colors = TextFieldDefaults.colors().copy(focusedContainerColor = color,
            unfocusedContainerColor = color)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDatePicker(
    title: String,
    initialDate: Long?,
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = initialDate
            ?: Clock.System.now().plus(24, DateTimeUnit.HOUR, TimeZone.currentSystemDefault())
                .toEpochMilliseconds()
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
            title = { Text(text = title, modifier = Modifier.padding(16.dp)) })
    }
}

@Composable
fun GoalTextFields(
    goalTitle: String,
    onTitleChange: (String) -> Unit,
    goalDescription: String?,
    onDescriptionChange: (String) -> Unit
) {
    val descriptionFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Title Text Field
    InputTextField(text = goalTitle,
        onTextChange = onTitleChange,
        label = "Title",
        imeAction = ImeAction.Next,
        onImeAction = { descriptionFocusRequester.requestFocus() },
        maxLines = 3)

    // Description Text Field
    if (goalDescription != null) {
        InputTextField(modifier = Modifier
            .fillMaxWidth()
            .focusRequester(descriptionFocusRequester),
            text = goalDescription,
            onTextChange = onDescriptionChange,
            label = "Description",
            imeAction = ImeAction.Done,
            onImeAction = { keyboardController?.hide() },
            maxLines = 7,
            color = Color.Transparent)
    }
}

@Composable
fun GoalDetailsTab(
    goal: Goal,
    editable: Boolean,
    onToggleEdit: () -> Unit,
    onEdited: (Goal) -> Unit,
    onDismiss: () -> Unit
) {
    var goalTitle by remember {
        mutableStateOf(goal.title)
    }
    var goalDescription by remember {
        mutableStateOf(goal.description)
    }
    var goalDeadline by remember {
        mutableStateOf(goal.deadline)
    }
    var deadlinePicker by remember {
        mutableStateOf(false)
    }
    Dialog(onDismissRequest = onDismiss) {
        Card(colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest)
        ) {
            Column(modifier = Modifier.padding(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Spacer(modifier = Modifier.height(16.dp))
                
                // Tab in Edit Mode
                if (editable) {
                    
                    // Title and Description Text Fields
                    GoalTextFields(
                        goalTitle = goalTitle,
                        onTitleChange = { newTitle ->
                            goalTitle = newTitle
                        },
                        goalDescription = goalDescription,
                        onDescriptionChange = { newDescription ->
                            goalDescription = newDescription
                        }
                    )
                    
                    // Add Description Button
                    if (goalDescription == null)
                        Button(onClick = { goalDescription = "" },
                            modifier = Modifier.padding(top = 4.dp)) {
                            Text(text = "Add Description")
                        }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    // Set Deadline Text Button
                    if (goalDeadline == null) {
                        TextButton(onClick = { deadlinePicker = !deadlinePicker },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.error)
                        ) {
                            Text(text = "Set deadline")
                        }
                    }
                    // Deadline and Edit Icon next to it
                    else {
                        Row {
                            Text(text = getDecoratedDeadline(goalDeadline!!),
                                modifier = Modifier.fillMaxWidth(0.7f),
                                style = LocalTextStyle.current.copy(fontSize = 12.sp))
                            Spacer(modifier = Modifier.width(2.dp))
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit",
                                modifier = Modifier.clickable { deadlinePicker = !deadlinePicker })
                        }
                    }
                }

                // Tab in View Mode
                else {
                    
                    // Title and Edit Icon
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = goal.title,
                            modifier = Modifier.fillMaxWidth(0.8f),
                            style = LocalTextStyle.current.copy(fontWeight = FontWeight.Bold,
                                fontSize = 20.sp)
                        )
                        IconButton(onClick = onToggleEdit) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                    
                    HorizontalDivider(modifier = Modifier.padding(top = 4.dp, bottom = 2.dp),
                        thickness = 2.dp)
                    
                    // Show Description
                    if (goal.description != null) {
                        Text(text = goal.description,
                            style = LocalTextStyle.current.copy(fontSize = 16.sp))
                        HorizontalDivider(modifier = Modifier.padding(vertical = 2.dp))
                    }
                    
                    // Show Deadline
                    if (goal.deadline != null) {
                        Text(text = getDecoratedDeadline(goal.deadline),
                            style = LocalTextStyle.current.copy(fontSize = 12.sp))
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Dialog Action Buttons
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End) {
                    if (editable)
                        TextButton(onClick = onToggleEdit,
                            modifier = Modifier.padding(end = 4.dp)) {
                            Text(text = "Cancel")
                        }
                    TextButton(onClick = { if (editable)
                        onEdited(Goal(id = goal.id,
                            title = goalTitle,
                            description = goalDescription?.ifBlank { null },
                            deadline = goalDeadline))
                        onDismiss() }
                    ) {
                        Text(text = if (editable) "Save" else "OK")
                    }
                }
            }
        }
    }
    if (deadlinePicker)
        ShowDatePicker(title = if (goalDeadline == null) "Set Deadline" else "Update Deadline",
            initialDate = goalDeadline,
            onDateSelected = { goalDeadline = it },
            onDismiss = { deadlinePicker = false })
}

@Preview
@Composable
fun DeleteConfirmationDialog(onConfirm: () -> Unit = {}, onDismiss: () -> Unit = {}) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = { TextButton(onClick = onConfirm) {
            Text(text = "Yes")
        } },
        dismissButton = { TextButton(onClick = onDismiss) {
            Text(text = "Cancel")
        } },
        icon = { Icon(imageVector = Icons.Filled.Warning, contentDescription = "Warning") },
        title = { Text(text = "Confirm Delete") },
        text = { Text(text = "Are you sure you want to delete this goal?") },
        iconContentColor = Color(0xFFFFA000)
    )
}