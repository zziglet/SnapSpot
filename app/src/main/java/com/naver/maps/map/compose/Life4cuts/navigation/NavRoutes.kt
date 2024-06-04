package com.naver.maps.map.compose.Life4cuts.navigation

sealed class NavRoutes (val route: String) {
    object Map : NavRoutes("map")
    object Photo : NavRoutes("Photo")
    object Album : NavRoutes("Album")
    object Settings : NavRoutes("Settings")
    object  Login : NavRoutes("Login")
    object Register : NavRoutes("Register")
}