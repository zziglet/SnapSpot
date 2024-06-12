package com.naver.maps.map.compose.Life4cuts.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

//(시현님 파트인 것 같아요)
// 로그인한 유저가 업로드한 리뷰의 사진을 그 user의 firebase에 모두 저장되어야 할 것 같아요!
// firebase에 저장된 이미지를 photoinfo(date, place, img url, userid)으로 선언되어야 할 것 같아요
// 저장하는 함수를 어떻게.. 어디서 구현하면 좋을까요..
val db = Firebase.firestore
data class PhotoInfo(val url: String, val description: String, val email: String)

fun AddPhotoInfo(photoInfo: PhotoInfo) {
    val user = FirebaseAuth.getInstance().currentUser
    db.collection("photos").add(photoInfo)
        .addOnSuccessListener { documentReference ->
            Log.d("Firestore", "DocumentSnapshot written with email: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w("Firestore", "Error adding document", e)
        }
}

//(지원 파트) 로그인한 유저가 업로드한 리뷰 사진을 그리드 형식으로 출력, 아직 기능 테스트 해보지 못 했습니다
@Composable
fun PhotosGrid() {
    val photos = remember { mutableStateListOf<PhotoInfo>() } // Photo는 사진 URL과 기타 정보를 포함하는 데이터 클래스

    // Firestore에서 사진 정보 읽어오기
    LaunchedEffect(Unit) {
        val db = Firebase.firestore
        db.collection("photos")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val photo = document.toObject(PhotoInfo::class.java)
                    photos.add(photo)
                }
            }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3), // 한 줄에 3개의 사진을 표시
        contentPadding = PaddingValues(8.dp),
        content = {
            items(photos.size) { index ->
                Image(
                    painter = rememberAsyncImagePainter(photos[index].url),
                    contentDescription = null,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    )
}

@Composable
fun AlbumScreen() {
    //addPhotoInfo()
    PhotosGrid()
}