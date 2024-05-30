package com.naver.maps.map.compose.demo.Life4cuts

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.week07.example5.NavRoutes
import com.example.week09.example5.screens.Contacts
import com.example.week09.example5.screens.Favorites
import com.naver.maps.map.compose.demo.Life4cuts.screens.NaverScreen


@Composable
fun NagivationHost(navController: NavHostController) {
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Map.route
    ){
        composable(NavRoutes.Map.route){
            NaverScreen()
        }
        composable(NavRoutes.Contacts.route){
            Contacts()
        }
        composable(NavRoutes.Favorites.route){
            Favorites()
        }
    }
}