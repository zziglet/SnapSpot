import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.io.File

class ReviewViewModel : ViewModel() {

    var reviews = mutableStateListOf<Pair<String, String>>() // Pair로 별점과 리뷰를 저장
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
            val lines = file.readLines()
            for (i in lines.indices step 2) {
                if (i + 1 < lines.size) {
                    reviews.add(Pair(lines[i], lines[i + 1]))
                }
            }
        } else {
            reviews.clear()
        }
    }

    @SuppressLint("SdCardPath")
    fun addReview(title: String, review: String) {
        val file = getReviewFile(title)
        val (rating, reviewText) = review.split("\n", limit = 2)
        file.appendText("$rating\n$reviewText\n")
        reviews.add(Pair(rating, reviewText))
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
