package com.project.dailyquest.widgets

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun UserForm(
    modifier: Modifier = Modifier,
    isNewUser: Boolean = false,
    onDone: (String, String) -> Unit = { _, _ -> }
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val passwordFocusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = modifier.padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.onSurface)
    ) {
        Column(
            modifier = Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputTextField(
                modifier = Modifier.padding(8.dp),
                text = email,
                onTextChange = { email = it },
                label = "Email",
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                onImeAction = { passwordFocusRequester.requestFocus() },
                color = MaterialTheme.colorScheme.surfaceContainerHigh
            )
            InputTextField(
                modifier = Modifier
                    .padding(8.dp)
                    .focusRequester(passwordFocusRequester),
                text = password,
                onTextChange = { password = it },
                label = "Password",
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done,
                onImeAction = { onDone(email, password)
                    keyboardController?.hide() },
                color = MaterialTheme.colorScheme.surfaceContainerHigh
            )
            Button(onClick = { onDone(email, password) }, modifier = Modifier.padding(8.dp)) {
                Text(text = if (isNewUser) "Sign Up" else "Login")
            }
        }
    }
}
