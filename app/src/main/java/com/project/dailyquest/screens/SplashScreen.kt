package com.project.dailyquest.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.project.dailyquest.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun SplashScreen(onSplashScreenFinish: () -> Unit = {}) {
    val scale = remember {
        Animatable(0f)
    }
    Surface(color = Color.White) {
        Box(modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center) {
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "App Logo",
                modifier = Modifier.scale(scale.value)
            )
        }
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 500)
        )
        launch { delay(2000L) }
        onSplashScreenFinish()
    }
}