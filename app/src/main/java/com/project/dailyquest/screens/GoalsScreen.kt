package com.project.dailyquest.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.dailyquest.R
import com.project.dailyquest.data.Goal
import com.project.dailyquest.data.getDummyGoals
import com.project.dailyquest.widgets.AppScaffold
import com.project.dailyquest.widgets.DisplayMainText
import com.project.dailyquest.widgets.InputTextField
import com.project.dailyquest.widgets.NavigationBar
import com.project.dailyquest.widgets.ShowDatePicker
import com.project.dailyquest.widgets.ShowGoal
import java.util.UUID

@Composable
fun GoalsScreen(
    viewGoals: Boolean,
    onToggleView: () -> Unit,
    addGoals: Boolean,
    onToggleAdd: () -> Unit,
    goals: List<Goal>,
    onDeleteGoal: (Goal) -> Unit,
    onAddGoal: (Goal) -> Unit,
    onEditGoal: (Goal) -> Unit,
    onNavigateToHome: () -> Unit
) {
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var deadlinePicker by remember {
        mutableStateOf(false)
    }
    var deadline: Long? = null
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    AppScaffold(title = "Goals",
        topAppBarColor = MaterialTheme.colorScheme.primaryContainer,
        homeButton = { IconButton(onClick = onNavigateToHome,
            modifier = Modifier.padding(end = 2.dp)) {
            Icon(imageVector = Icons.Default.Home,
                contentDescription = "Home")
            }
        }) { innerPadding ->
        Column(modifier = Modifier
            .padding(innerPadding)
            .padding(20.dp)
            .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            DisplayMainText(key = "Active Goals: ", value = "3", fontSize = 42.sp)
            Spacer(modifier = Modifier.height(15.dp))
            Row {
                Button(onClick = { if (addGoals) onToggleAdd()
                    onToggleView() }) {
                    Icon(painter = if (viewGoals) painterResource(id = R.drawable.hide)
                        else painterResource(id = R.drawable.view),
                        contentDescription = "View/Hide")
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = if (viewGoals) "Hide" else "View")
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(onClick = { if (viewGoals) onToggleView()
                    onToggleAdd() },
                    enabled = !addGoals) {
                    Icon(imageVector = Icons.Default.Add,
                        contentDescription = "Add")
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = "Add")
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            AnimatedVisibility(visible = viewGoals,
                modifier = Modifier.heightIn(max = 350.dp)) {
                LazyColumn {
                    items(items = goals) {
                        ShowGoal(goal = it,
                            onEditGoal = {},
                            onDeleteGoal = { onDeleteGoal(it) })
                    }
                }
            }
            AnimatedVisibility(visible = addGoals,
                modifier = Modifier.heightIn(max = 350.dp)) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    InputTextField(text = title,
                        onTextChange = { newText ->
                            title = newText
                        },
                        label = "Title",
                        imeAction = ImeAction.Next,
                        onImeAction = { keyboardController?.hide() },
                        maxLines = 2)
                    InputTextField(modifier = Modifier.fillMaxWidth(),
                        text = description,
                        onTextChange = { newText ->
                            description = newText
                        },
                        label = "Description",
                        imeAction = ImeAction.Done,
                        onImeAction = { keyboardController?.hide() },
                        maxLines = 7,
                        color = Color.Transparent)
                    Spacer(modifier = Modifier.height(10.dp))
                    TextButton(onClick = { deadlinePicker = !deadlinePicker },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.error)
                    ) {
                        Text(text = "Set deadline")
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Button(onClick = { onAddGoal(Goal(id = UUID.randomUUID(),
                            title = title,
                            description = description,
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
                        ShowDatePicker(onDateSelected = { deadline = it },
                            onDismiss = { deadlinePicker = false })
                }
            }
            NavigationBar(disabledButton = "Configure Goals")
        }
    }
}

@Preview
@Composable
fun GoalsScreenPreview() {
    GoalsScreen(
        viewGoals = true,
        onToggleView = {},
        addGoals = false,
        onToggleAdd = {},
        goals = getDummyGoals(),
        onDeleteGoal = {},
        onAddGoal = {},
        onEditGoal = {},
        onNavigateToHome = {}
    )
}