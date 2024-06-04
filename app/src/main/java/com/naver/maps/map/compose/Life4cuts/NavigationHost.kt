package com.naver.maps.map.compose.Life4cuts

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.naver.maps.map.compose.Life4cuts.screens.Contacts
import com.naver.maps.map.compose.Life4cuts.screens.Favorites
import com.naver.maps.map.compose.Life4cuts.screens.LoginScreen
import com.naver.maps.map.compose.Life4cuts.screens.NaverScreen
import com.naver.maps.map.compose.Life4cuts.screens.RegisterScreen


@Composable
fun NagivationHost(navController: NavHostController, auth: FirebaseAuth) {
    val upPress: () -> Unit = {
        navController.navigateUp()
    }

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Login.route
    ){
        composable(NavRoutes.Login.route){
            LoginScreen(navController,auth)
        }
        composable(NavRoutes.Register.route){
            RegisterScreen(navController,auth)
        }
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