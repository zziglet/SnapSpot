package com.naver.maps.map.compose.Life4cuts.navigation

import AccountSettingsScreen
import FavoritesScreen
import ProfileScreen
import PublicSettingScreen
import ReviewViewModel
import SettingsScreen
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.naver.maps.map.compose.Life4cuts.screens.AlbumScreen
import com.naver.maps.map.compose.Life4cuts.screens.BookmarkScreen
import com.naver.maps.map.compose.Life4cuts.screens.FavoriteViewModel
import com.naver.maps.map.compose.Life4cuts.screens.HomeScreen
import com.naver.maps.map.compose.Life4cuts.screens.LoginScreen
import com.naver.maps.map.compose.Life4cuts.screens.PhotoScreen
import com.naver.maps.map.compose.Life4cuts.screens.PlaceScreen
import com.naver.maps.map.compose.Life4cuts.screens.RegisterScreen
import com.naver.maps.map.compose.Life4cuts.screens.ReviewScreen

//import com.naver.maps.map.compose.Life4cuts.screens.SettingsScreen


@Composable
fun NagivationHost(navController: NavHostController, auth: FirebaseAuth , firestore: FirebaseFirestore) {

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Login.route
    ){
        composable(NavRoutes.Login.route){
            LoginScreen(navController,auth , firestore)
        }
        composable(NavRoutes.Register.route){
            RegisterScreen(navController,auth)
        }
        composable(NavRoutes.Home.route){
            HomeScreen(navController)
        }
        composable(NavRoutes.Photo.route){
            PhotoScreen(auth,firestore)
        }
        composable(NavRoutes.Album.route){
            AlbumScreen()
        }
        composable(NavRoutes.Place.route){
            val viewModel: FavoriteViewModel = viewModel()
            PlaceScreen(navController, viewModel, firestore)
        }
        composable(NavRoutes.Bookmark.route){
            BookmarkScreen()
        }
        composable(NavRoutes.MyAlbum.route){
            AlbumScreen()
        }
        composable(
            route = NavRoutes.Review.route,
            arguments = listOf(
                navArgument("caption") { type = NavType.StringType },
                navArgument("address") { type = NavType.StringType },
                navArgument("img") { type = NavType.IntType },
                navArgument("title") { type = NavType.StringType },
                navArgument("hashtag") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val caption = backStackEntry.arguments?.getString("caption") ?: ""
            val address = backStackEntry.arguments?.getString("address") ?: ""
            val img = backStackEntry.arguments?.getInt("img") ?: 0
            val title = backStackEntry.arguments?.getString("title") ?: ""
            val hashtag = backStackEntry.arguments?.getString("hashtag") ?: ""
            val
                viewModel: ReviewViewModel = viewModel()
            ReviewScreen(caption, address, img, title, hashtag, viewModel, navController, firestore)
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