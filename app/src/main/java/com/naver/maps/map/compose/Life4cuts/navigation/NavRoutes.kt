package com.naver.maps.map.compose.Life4cuts.navigation

sealed class NavRoutes (val route: String) {
    object Home : NavRoutes("Home")
    object Photo : NavRoutes("Photo")
    object Album : NavRoutes("Album")
    //
    object Settings : NavRoutes("Settings")
    object profile : NavRoutes("profile")
    object public : NavRoutes("public")
    object favorites : NavRoutes("favorites")
    object account : NavRoutes("account")
    //
    object  Login : NavRoutes("Login")
    object Register : NavRoutes("Register")
}