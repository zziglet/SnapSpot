package com.naver.maps.map.compose.Life4cuts.navigation

sealed class NavRoutes(val route: String) {
    object Home : NavRoutes("Home")
    object Photo : NavRoutes("Photo")
    object Album : NavRoutes("Album")

    object Review : NavRoutes("review/{caption}/{address}/{img}/{title}/{hashtag}")

    //
    object Settings : NavRoutes("Settings")
    object profile : NavRoutes("profile")

    //
    object Login : NavRoutes("Login")
    object Register : NavRoutes("Register")
    object Place : NavRoutes("Place")
    object Bookmark : NavRoutes("Bookmark")
    object MyAlbum : NavRoutes("MyAlbum")
}