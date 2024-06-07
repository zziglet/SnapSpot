package com.naver.maps.map.compose.Life4cuts.navigation

import AccountSettingsScreen
import FavoritesScreen
import ProfileScreen
import PublicSettingScreen
import SettingsScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.firebase.auth.FirebaseAuth
import com.naver.maps.map.compose.Life4cuts.screens.AlbumScreen
import com.naver.maps.map.compose.Life4cuts.screens.HomeScreen
import com.naver.maps.map.compose.Life4cuts.screens.LoginScreen
import com.naver.maps.map.compose.Life4cuts.screens.PhotoScreen
import com.naver.maps.map.compose.Life4cuts.screens.RegisterScreen
//import com.naver.maps.map.compose.Life4cuts.screens.SettingsScreen


@Composable
fun NagivationHost(navController: NavHostController, auth: FirebaseAuth) {

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
        composable(NavRoutes.Home.route){
            HomeScreen()
        }
        composable(NavRoutes.Photo.route){
            PhotoScreen()
        }
        composable(NavRoutes.Album.route){
            AlbumScreen()
        }

        //
        composable(NavRoutes.Settings.route){
            SettingsScreen(navController)
        }
        composable(route = NavRoutes.profile.route) {
            ProfileScreen(navController)
        }

        composable(route = NavRoutes.public.route) {
            PublicSettingScreen(navController)
        }

        composable(route = NavRoutes.favorites.route) {
            FavoritesScreen(navController)
        }

        composable(route = NavRoutes.account.route) {
            AccountSettingsScreen(navController)
        }
        //
    }
}