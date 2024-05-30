package com.example.week07.example5

sealed class NavRoutes (val route: String) {
    object Map : NavRoutes("map")
    object Contacts : NavRoutes("Contacts")
    object Favorites : NavRoutes("Favorites")
}