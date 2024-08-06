package com.project.dailyquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.project.dailyquest.navigation.AppNavigation
import com.project.dailyquest.ui.theme.DailyQuestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DailyQuestTheme {
                GoalTrackingApp {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun GoalTrackingApp(content: @Composable () -> Unit) {
    content()
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DailyQuestTheme {
        GoalTrackingApp {
            AppNavigation()
        }
    }
}