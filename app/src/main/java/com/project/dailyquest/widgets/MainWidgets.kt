package com.project.dailyquest.widgets

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.dailyquest.R

@Preview(showBackground = true)
@Composable
fun Dashboard(
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
            MainText(key = "Currently Active Goals: ",
                value = "$goalCount",
                fontSize = 20.sp)
            HorizontalDivider(thickness = 4.dp,
                color = MaterialTheme.colorScheme.surface)
            MainText(key = "Today's Pending Tasks: ",
                value = "$pendingTasks",
                fontSize = 20.sp)
            HorizontalDivider(thickness = 4.dp,
                color = MaterialTheme.colorScheme.surface)
            MainText(key = "Average sleep/day: ",
                value = "$avgSleep hrs",
                fontSize = 20.sp)
        }
    }
}

@Composable
fun MainText(key: String, value: String, fontSize: TextUnit) {
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
fun InputTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    label: String,
    isInvalid: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Default,
    onImeAction: () -> Unit = {},
    maxLines: Int = 1,
    color: Color = MaterialTheme.colorScheme.surfaceContainer
) {
    var passwordVisibility by remember { mutableStateOf(false) }
    TextField(
        value = text, onValueChange = onTextChange,
        modifier = modifier.padding(4.dp),
        label = { Text(text = label) },
        trailingIcon = { if (keyboardType == KeyboardType.Password && text.isNotBlank())
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(painter = painterResource(
                    id = if (passwordVisibility) R.drawable.hide else R.drawable.view),
                    contentDescription = "View/Hide")
            }
        },
        isError = isInvalid,
        visualTransformation = if (keyboardType == KeyboardType.Password && !passwordVisibility)
            PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions { onImeAction() },
        maxLines = maxLines,
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = color,
            unfocusedContainerColor = color
        )
    )
}

@Preview
@Composable
fun ConfirmationDialog(
    action: String = "Delete",
    msg: String = "Are you sure you want to delete this note?",
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Yes")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        },
        icon = { Icon(imageVector = Icons.Filled.Warning, contentDescription = "Warning") },
        title = { Text(text = "Confirm $action") },
        text = { Text(text = msg) },
        iconContentColor = Color(0xFFFFA000)
    )
}
