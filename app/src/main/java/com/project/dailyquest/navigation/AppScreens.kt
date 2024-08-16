package com.project.dailyquest.navigation

enum class AppScreens {
    SplashScreen,
    HomeScreen,
    GoalsScreen;

    companion object {
        fun fromRoute(route: String?): AppScreens? = when(route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            HomeScreen.name -> HomeScreen
            GoalsScreen.name -> GoalsScreen
            null -> null
            else -> throw IllegalArgumentException("Route $route is not recognized!")
        }
    }
}