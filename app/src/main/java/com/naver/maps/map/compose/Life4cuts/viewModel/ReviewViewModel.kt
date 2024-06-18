package com.naver.maps.map.compose.Life4cuts.viewModel

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File

class ReviewViewModel : ViewModel() {
    var reviews = mutableStateListOf<String>()
        private set

    var userId: String? = null
        private set

    init {
        val auth = FirebaseAuth.getInstance()
        userId = auth.currentUser?.uid
    }

    @SuppressLint("SdCardPath")
    private fun getReviewFile(title: String): File {
        return File("/data/data/com.naver.maps.map.compose.demo/files/${title}_reviews.txt")
    }

    @SuppressLint("SdCardPath")
    fun loadReviews(caption: String) {
        val file = getReviewFile(caption)
        if (file.exists()) {
            reviews.clear()
            reviews.addAll(file.readLines())
        } else {
            reviews.clear()
        }
    }

    @SuppressLint("SdCardPath")
    fun addReview(title: String, review: String) {
        val file = getReviewFile(title)
        file.appendText("$review\n")
        reviews.add(review)
    }

    // 즐겨찾기 추가
    private val firestore = FirebaseFirestore.getInstance()

    fun toggleFavorite(title: String) {
        userId?.let { uid ->
            val favoritesRef = firestore.collection("users").document(uid).collection("favorites")
            val favoriteDoc = favoritesRef.document(title)

            favoriteDoc.get().addOnSuccessListener { document ->
                if (document.exists()) {
                    // If already favorite, remove it
                    favoriteDoc.delete()
                } else {
                    // If not favorite, add it
                    val data = hashMapOf("title" to title)
                    favoriteDoc.set(data)
                }
            }
        }
    }

    fun isFavorite(title: String, onComplete: (Boolean) -> Unit) {
        userId?.let { uid ->
            val favoritesRef = firestore.collection("users").document(uid).collection("favorites")
            favoritesRef.document(title).get().addOnSuccessListener { document ->
                onComplete(document.exists())
            }
        }
    }
}
