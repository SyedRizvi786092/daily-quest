package com.project.dailyquest.navigation

enum class AppScreens {
    SplashScreen,
    LoginScreen,
    ProfileScreen,
    HomeScreen,
    GoalsScreen;

    companion object {
        fun fromRoute(route: String?): AppScreens? = when(route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            ProfileScreen.name -> ProfileScreen
            HomeScreen.name -> HomeScreen
            GoalsScreen.name -> GoalsScreen
            null -> null
            else -> throw IllegalArgumentException("Route $route is not recognized!")
        }
    }
}
