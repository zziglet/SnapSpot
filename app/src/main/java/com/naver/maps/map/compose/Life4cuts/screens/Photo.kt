package com.naver.maps.map.compose.Life4cuts.screens

import android.content.Context
import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.naver.maps.map.compose.demo.R
import kotlinx.coroutines.tasks.await


/////

//기존 LoadPhotosFromLocal함수는 Composable 함수라 같은 기능하는 함수 추가
fun loadAllPhotosFromLocal(context: Context): List<Int> {
    val drawableClass = R.drawable::class.java
    val fieldList = drawableClass.fields
    val resourceIdList = mutableListOf<Int>()

    for (field in fieldList) {
        try {
            val resourceId = field.getInt(null)
            val resourceName = context.resources.getResourceEntryName(resourceId)
            if (resourceName.startsWith("pic")) {
                resourceIdList.add(resourceId)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return resourceIdList
}


// 사진 클릭하여 확대 시 북마크 on/off 기능 가능
@Composable
fun PhotoScreen(auth: FirebaseAuth, firestore: FirebaseFirestore, bookmarkedImages: Set<String>, onUpdateBookmarks: (Set<String>) -> Unit) {
    val context = LocalContext.current
    val resourceIds = loadAllPhotosFromLocal(context)
    val imageList = remember { mutableStateOf<List<ImageBitmap>>(emptyList()) }
    val showDialog = remember { mutableStateOf(false) }
    val selectedImage = remember { mutableStateOf<ImageBitmap?>(null) }
    val selectedImageId = remember { mutableStateOf<String?>(null) }
    val currentUser = auth.currentUser

    // Firestore에서 북마크 불러오기
    LaunchedEffect(currentUser) {
        currentUser?.let {
            val docRef = firestore.collection("users").document(it.uid).collection("bookmarks")
            val snapshot = docRef.get().await()
            val bookmarks = snapshot.documents.map { it.id }
            onUpdateBookmarks(bookmarks.toSet())
        }
    }

    LaunchedEffect(resourceIds) {
        val bitmaps = resourceIds.map { resId ->
            BitmapFactory.decodeResource(context.resources, resId).asImageBitmap()
        }
        imageList.value = bitmaps
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (currentUser != null) {
            if (imageList.value.isNotEmpty()) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(imageList.value.size) { index ->
                        val image = imageList.value[index]
                        val imageId = "image_$index"
                        val isBookmarked = bookmarkedImages.contains(imageId)
                        Image(
                            bitmap = image,
                            contentDescription = null,
                            modifier = Modifier
                                .size(150.dp)
                                .clickable {
                                    selectedImage.value = image
                                    selectedImageId.value = imageId
                                    showDialog.value = true
                                },
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        } else {
            Text(text = "로그인이 필요합니다.")
        }
    }

    if (showDialog.value) {
        selectedImage.value?.let { image ->
            val imageId = selectedImageId.value ?: return@let
            val isBookmarked = bookmarkedImages.contains(imageId)
            AlertDialog(
                containerColor = Color.White,
                onDismissRequest = { showDialog.value = false },
                text = {
                    Image(
                        bitmap = image,
                        contentDescription = null,
                        modifier = Modifier.size(500.dp)
                    )
                    Icon(
                        imageVector = if (isBookmarked) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                        tint = Color.Red,
                        contentDescription = null,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                val newBookmarks = bookmarkedImages.toMutableSet()
                                if (isBookmarked) {
                                    newBookmarks.remove(imageId)
                                    firestore.collection("users").document(currentUser!!.uid)
                                        .collection("bookmarks").document(imageId).delete()
                                } else {
                                    newBookmarks.add(imageId)
                                    firestore.collection("users").document(currentUser!!.uid)
                                        .collection("bookmarks").document(imageId).set(mapOf("id" to imageId))
                                }
                                onUpdateBookmarks(newBookmarks)
                            }
                    )
                },
                confirmButton = {
                    IconButton(onClick = { showDialog.value = false  }) {
                        Icon(imageVector = Icons.Default.Close,
                            tint = Color.Black,
                            contentDescription = "Close")
                    }
                }
            )
        }
    }
}


/////////






// 임시로 login한 User의 Data 불러오기 코드 추가했습니다.
@Composable
fun UserDataScreen(uid: String, firestore: FirebaseFirestore) {
    var userData by remember { mutableStateOf<List<String>?>(null) }

    LaunchedEffect(uid) {
        userData = getUserData(firestore, uid)
    }

    if (userData != null) {
        for (data in userData!!) {
            Text(text = data)
        }
    } else {
        Text(text = "Loading...")
    }
}

private suspend fun getUserData(firestore: FirebaseFirestore, uid: String): List<String>? {
    return try {
        val userCollection = firestore.collection("users").document(uid).collection("userData")
        val documents = userCollection.get().await()
        documents.mapNotNull { it.getString("dataField") }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
