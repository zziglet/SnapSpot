package com.naver.maps.map.compose.Life4cuts.navigation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.naver.maps.map.compose.Life4cuts.viewModel.FavoriteViewModel
import com.naver.maps.map.compose.Life4cuts.screens.HomeScreen
import com.naver.maps.map.compose.Life4cuts.screens.LoginScreen
import com.naver.maps.map.compose.Life4cuts.screens.PhotoScreen
import com.naver.maps.map.compose.Life4cuts.screens.PlaceScreen
import com.naver.maps.map.compose.Life4cuts.screens.ProfileScreen
import com.naver.maps.map.compose.Life4cuts.screens.RegisterScreen
import com.naver.maps.map.compose.Life4cuts.screens.ReviewScreen
import com.naver.maps.map.compose.Life4cuts.screens.SettingsScreen
import kotlinx.coroutines.tasks.await


@Composable
fun NavigationHost(
    navController: NavHostController,
    auth: FirebaseAuth,
    firestore: FirebaseFirestore,
) {
    //북마크 이미지 저장을 위해 추가
    var bookmarkedImages by remember { mutableStateOf(setOf<String>()) }

    LaunchedEffect(auth.currentUser) {
        auth.currentUser?.let {
            val docRef = firestore.collection("users").document(it.uid).collection("bookmarks")
            val snapshot = docRef.get().await()
            val bookmarks = snapshot.documents.map { it.id }
            bookmarkedImages = bookmarks.toSet()
        }
    }

    NavHost(
        navController = navController,
        startDestination = NavRoutes.Login.route
    ) {
        composable(NavRoutes.Login.route) {
            LoginScreen(navController, auth, firestore)
        }
        composable(NavRoutes.Register.route) {
            RegisterScreen(navController, auth)
        }
        composable(NavRoutes.Home.route) {
            HomeScreen(navController)
        }
        composable(NavRoutes.Photo.route) {
            //PhotoScreen 수정
            PhotoScreen(
                auth = auth,
                firestore = firestore,
                bookmarkedImages = bookmarkedImages,
                onUpdateBookmarks = { newBookmarks -> bookmarkedImages = newBookmarks }
            )

        }
        composable(NavRoutes.Album.route) {
            AlbumScreen(navController)
        }
        composable(NavRoutes.Place.route) {
            val viewModel: FavoriteViewModel = viewModel()
            PlaceScreen(navController, viewModel, firestore)
        }
        composable(NavRoutes.Bookmark.route) {
            //BookmarkScreen 수정
            BookmarkScreen(
                auth = auth,
                firestore = firestore,
                bookmarkedImages = bookmarkedImages,
                onUpdateBookmarks = { newBookmarks -> bookmarkedImages = newBookmarks }
            )

        }
        composable(NavRoutes.MyAlbum.route) {
            AlbumScreen(navController)
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
            ReviewScreen(caption, address, img, title, hashtag, navController, firestore)
        }


        composable(NavRoutes.Settings.route) {
            val favoriteViewModel: FavoriteViewModel = viewModel()
            SettingsScreen(navController, auth, firestore, favoriteViewModel, onClearBookmarks =
            { bookmarkedImages = emptySet() })
        }
        composable(route = NavRoutes.profile.route) {
            ProfileScreen(navController, auth, firestore)
        }


    }
}