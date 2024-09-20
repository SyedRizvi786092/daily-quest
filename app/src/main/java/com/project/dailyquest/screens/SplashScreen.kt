package com.project.dailyquest.screens

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.dailyquest.R
import com.project.dailyquest.model.AuthState

@Preview(showBackground = true)
@Composable
fun SplashScreen(
    userDataRefreshState: AuthState = AuthState(),
    onSplashScreenFinish: () -> Unit = {}
) {
    val scale = remember { Animatable(0f) }
    var hasAnimated by remember { mutableStateOf(false) }

    // Navigation Effect
    LaunchedEffect(key1 = userDataRefreshState, key2 = hasAnimated) {
        if (hasAnimated) {
            when (val status = userDataRefreshState.status) {
                is AuthState.Status.LOADING -> {/* Wait for completion */}
                is AuthState.Status.IDLE -> onSplashScreenFinish()
                is AuthState.Status.COMPLETE -> {
                    when (status.result) {
                        is AuthState.Status.COMPLETE.Result.SUCCESS -> onSplashScreenFinish()
                        is AuthState.Status.COMPLETE.Result.ERROR -> {/* todo */}
                    }
                }
            }
        }
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            brush = Brush.linearGradient(
                listOf(Color.White, Color.LightGray, Color.Gray, Color.DarkGray)
            )
        ),
        contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier.scale(scale.value)
            )
//            Spacer(modifier = Modifier.height(24.dp))
//            if (authState.status is AuthState.Status.LOADING) CircularProgressIndicator()
//            Log.d("Splash", "SplashScreen: $userDataRefreshState")
        }
    }

    // Animation Effect
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.8f,
            animationSpec = tween(durationMillis = 500)
        )
        hasAnimated = true
    }
}
