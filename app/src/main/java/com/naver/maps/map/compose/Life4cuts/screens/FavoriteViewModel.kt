package com.naver.maps.map.compose.Life4cuts.screens

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.Life4cuts.data.PhotoBoothInfo

class FavoriteViewModel : ViewModel() {
    var favorites = mutableStateListOf<PhotoBoothInfo>()
        private set

    var userId: String? = null
        private set

    init {
        val auth = FirebaseAuth.getInstance()
        userId = auth.currentUser?.uid
    }

    fun loadFavorites(firestore: FirebaseFirestore) {
        val currentUser = userId
        if (currentUser != null) {
            firestore.collection("users")
                .document(currentUser)
                .collection("favorites")
                .get()
                .addOnSuccessListener { documents ->
                    favorites.clear()
                    for (document in documents) {
                        val caption = document.getString("caption") ?: ""
                        val address = document.getString("address") ?: ""
                        val imgId = document.getLong("imgId")?.toInt() ?: 0
                        val title = document.getString("title") ?: ""
                        val hashtag = document.getString("hashtag") ?: ""
                        favorites.add(PhotoBoothInfo(LatLng(0.0, 0.0), caption, address, imgId, title, hashtag))
                    }
                }
        }
    }
}
