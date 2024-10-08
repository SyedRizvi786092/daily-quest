package com.project.dailyquest.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.dailyquest.R
import com.project.dailyquest.model.AuthState
import com.project.dailyquest.widgets.UserForm

@Preview
@Composable
fun LoginScreen(
    loginLoadingState: AuthState = AuthState(),
    signIn: (String, String) -> Unit = { _, _ -> },
    signUp: (String, String) -> Unit = { _, _ -> }
) {
    var loginState by remember { mutableStateOf(true) }

    Surface(modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surfaceContainerHighest) {
        Column(modifier = Modifier
            .padding(start = 12.dp, top = 36.dp, end = 12.dp, bottom = 12.dp)
            .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally) {

            // Logo Image
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(160.dp)
            )

            // Email and Password Instruction
            if (!loginState) {
                Text(text = "Please enter a valid email address and a password that is at least" +
                        " 6 characters long.",
                    fontSize = 12.sp,
                    modifier = Modifier.padding(4.dp))
            }

            // Text Fields and Button
            UserForm(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 12.dp),
                isNewUser = !loginState,
                onDone = if (loginState) signIn else signUp
            )

            // Loading Indicator
            if (loginLoadingState.status == AuthState.Status.LOADING) {
                Column(modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(2.dp))
                    loginLoadingState.msg?.let {
                        Text(text = it, fontSize = 12.sp)
                    }
                }
            }

            // Login/Sign Up Toggle
            else {
                Row(modifier = Modifier.padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = if (loginState) "New User?" else "Already have an account?")
                    TextButton(onClick = { loginState = !loginState },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = MaterialTheme.colorScheme.surfaceTint)
                    ) {
                        Text(text = if (loginState) "Sign Up" else "Login",
                            modifier = Modifier.align(Alignment.CenterVertically),
                            fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
