package com.project.dailyquest.screens.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseUser
import com.project.dailyquest.model.AuthState
import com.project.dailyquest.widgets.InputTextField

//@Preview
@Composable
fun ProfileScreen(
    scaffoldPadding: PaddingValues = PaddingValues(),
    authState: AuthState = AuthState(),
    user: FirebaseUser,
    onAddName: (String) -> Unit = {},
    onSkip: () -> Unit = {}
) {
    var name by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    var showNameHint by remember { mutableStateOf(false) }
    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(scaffoldPadding),
        color = MaterialTheme.colorScheme.surfaceContainerHighest) {
        if (user.displayName == null) {
            Column(modifier = Modifier
                .padding(12.dp)
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text(text = "Let's get you started!",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif)
                Spacer(modifier = Modifier.height(48.dp))
                Text(text = "What should we call you?")
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    InputTextField(
                        text = name,
                        onTextChange = { name = it },
                        label = "Your Name",
                        imeAction = ImeAction.Done,
                        onImeAction = {
                            keyboardController?.hide()
                            if (name.trim().length > 1) onAddName(name)
                        },
                        color = MaterialTheme.colorScheme.surfaceContainerLowest
                    )
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = "Info",
                        modifier = Modifier
                            .padding(start = 2.dp)
                            .clickable { showNameHint = !showNameHint }
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                AnimatedVisibility(visible = showNameHint) {
                    Text(text = "Info: Type your first name or nickname",
                        fontSize = 10.sp)
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = { onAddName(name) },
                    enabled = name.trim().length > 1) {
                    Text(text = "Take me Home")
                }
                Spacer(modifier = Modifier.height(4.dp))
                if (authState.status == AuthState.Status.LOADING) CircularProgressIndicator()
                else TextButton(onClick = onSkip,
                    modifier = Modifier
                        .padding(end = 24.dp).
                        align(Alignment.End)) {
                    Text(text = "Skip")
                }
            }
        } else {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center) {
                Text(text = "TODO: Yet to be implemented!")
            }
        }
    }
}
