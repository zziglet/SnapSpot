package com.naver.maps.map.compose.Life4cuts.screens

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.naver.maps.map.compose.Life4cuts.navigation.NavRoutes
import com.naver.maps.map.compose.Life4cuts.viewModel.FavoriteViewModel

@Composable
fun PlaceScreen(navController: NavController, favoriteViewModel: FavoriteViewModel, firestore: FirebaseFirestore) {
    val userId = favoriteViewModel.userId

    LaunchedEffect(userId) {
        if (userId.isNullOrBlank()) {
            Log.d("UserIdCheck", "UserId is null or blank")
            navController.navigate(NavRoutes.Login.route) {
                popUpTo(NavRoutes.Login.route) { inclusive = true }
            }
        } else {
            Log.d("UserIdCheck", "UserId: $userId")
            favoriteViewModel.loadFavorites(firestore)
        }
    }

    val favorites = favoriteViewModel.favorites

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(items = favorites) { favorite ->
            ShowPhotoBooth(
                userId = userId ?: "",
                caption = favorite.caption,
                address = favorite.address,
                imgId = favorite.imgId,
                title = favorite.title,
                hashtag = favorite.hashtag,
                firestore = firestore
            )
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
