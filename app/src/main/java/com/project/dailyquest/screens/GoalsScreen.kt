package com.project.dailyquest.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.dailyquest.R
import com.project.dailyquest.data.Goal
import com.project.dailyquest.data.getDummyGoals
import com.project.dailyquest.widgets.DeleteConfirmationDialog
import com.project.dailyquest.widgets.DisplayMainText
import com.project.dailyquest.widgets.GoalDetailsTab
import com.project.dailyquest.widgets.GoalTextFields
import com.project.dailyquest.widgets.ShowDatePicker
import com.project.dailyquest.widgets.ShowGoal
import java.util.UUID

@Composable
fun GoalsScreen(
    scaffoldPadding: PaddingValues,
    viewGoals: Boolean,
    onToggleView: () -> Unit,
    addGoals: Boolean,
    onToggleAdd: () -> Unit,
    goals: List<Goal>,
    onDeleteGoal: (Goal) -> Unit,
    onAddGoal: (Goal) -> Unit,
    onEditGoal: (Goal) -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var deadlinePicker by remember { mutableStateOf(false) }
    var deadline: Long? = null
    var showGoalDetails by remember { mutableStateOf(false) }
    var selectedGoal by remember { mutableStateOf<Goal?>(null) }
    var goalEditState by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Column(modifier = Modifier
        .padding(scaffoldPadding)
        .padding(16.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally) {

        // Displaying Current Active Goals
        DisplayMainText(key = "Active Goals: ", value = "3", fontSize = 40.sp)

        Spacer(modifier = Modifier.height(12.dp))

        // Row for View and Add Buttons
        Row {
            Button(onClick = { if (addGoals) onToggleAdd()
                onToggleView() }) {
                Icon(painter = if (viewGoals) painterResource(id = R.drawable.hide)
                else painterResource(id = R.drawable.view),
                    contentDescription = "View/Hide")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = if (viewGoals) "Hide" else "View")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { if (viewGoals) onToggleView()
                onToggleAdd() },
                enabled = !addGoals) {
                Icon(imageVector = Icons.Default.Add,
                    contentDescription = "Add")
                Spacer(modifier = Modifier.width(4.dp))
                Text(text = "Add")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // View Goals Tab
        AnimatedVisibility(visible = viewGoals,
            modifier = Modifier.heightIn(max = 400.dp)) {
            LazyColumn {
                items(items = goals) { currentGoal ->
                    ShowGoal(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            selectedGoal = currentGoal
                            goalEditState = false
                            showGoalDetails = true
                        },
                        goal = currentGoal,
                        onEditGoal = { selectedGoal = currentGoal
                            goalEditState = true
                            showGoalDetails = true },
                        onDeleteGoal = { selectedGoal = currentGoal
                            showDeleteDialog = true }
                    )
                }
            }
        }

        // Add Goals Tab
        AnimatedVisibility(visible = addGoals,
            modifier = Modifier.heightIn(max = 400.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                // Display Title and Description Text Fields
                GoalTextFields(
                    goalTitle = title,
                    onTitleChange = { newTitle ->
                        title = newTitle
                    },
                    goalDescription = description,
                    onDescriptionChange = { newDescription ->
                        description = newDescription
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Set Deadline Text Button
                TextButton(onClick = { deadlinePicker = true },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error)
                ) {
                    Text(text = "Set deadline")
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Save Goal Button
                Button(onClick = { onAddGoal(Goal(id = UUID.randomUUID(),
                    title = title,
                    description = description.ifBlank { null },
                    deadline = deadline))
                    Toast.makeText(context, "Goal Added!", Toast.LENGTH_SHORT).show()
                    title = ""
                    description = ""
                    onToggleAdd()
                    onToggleView() },
                    enabled = title.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text(text = "Save")
                }
                if (deadlinePicker)
                    ShowDatePicker(title = "Set Deadline",
                        initialDate = deadline,
                        onDateSelected = { deadline = it },
                        onDismiss = { deadlinePicker = false })
            }
        }
    }

    // Show Goal details when a goal is clicked
    if (showGoalDetails && selectedGoal != null)
        GoalDetailsTab(
            goal = selectedGoal!!,
            editable = goalEditState,
            onToggleEdit = { goalEditState = !goalEditState },
            onEdited = { updatedGoal -> onEditGoal(updatedGoal) },
            onDismiss = { selectedGoal = null
                showGoalDetails = false }
        )

    // Show confirmation dialog when user tries to delete a goal
    if (showDeleteDialog && selectedGoal != null)
        DeleteConfirmationDialog(
            onConfirm = { onDeleteGoal(selectedGoal!!)
                selectedGoal = null
                showDeleteDialog = false },
            onDismiss = { selectedGoal = null
                showDeleteDialog = false }
        )
}

@Preview(showBackground = true)
@Composable
fun GoalsScreenPreview() {
    GoalsScreen(
        scaffoldPadding = PaddingValues(),
        viewGoals = true,
        onToggleView = {},
        addGoals = false,
        onToggleAdd = {},
        goals = getDummyGoals(),
        onDeleteGoal = {},
        onAddGoal = {},
        onEditGoal = {}
    )
}