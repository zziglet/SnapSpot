package com.naver.maps.map.compose.Life4cuts

sealed class NavRoutes (val route: String) {
    object Map : NavRoutes("map")
    object Contacts : NavRoutes("Contacts")
    object Favorites : NavRoutes("Favorites")
}