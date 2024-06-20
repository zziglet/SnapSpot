package com.naver.maps.map.compose.Life4cuts.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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


// 로그인 시 기존 북마크 기록 남음, 사진을 선택하여 확대한 경우 북마크 해제 가능, 이때 이 작업은 전체 포즈 출력의 이미지에도 동일하게 적용됨
@Composable
fun BookmarkScreen(
    auth: FirebaseAuth,
    firestore: FirebaseFirestore,
    bookmarkedImages: Set<String>,
    onUpdateBookmarks: (Set<String>) -> Unit,
) {
    val context = LocalContext.current
    val imageList = remember { mutableStateOf<List<Pair<String, ImageBitmap>>>(emptyList()) }
    val showDialog = remember { mutableStateOf(false) }
    val selectedImage = remember { mutableStateOf<ImageBitmap?>(null) }
    val selectedImageId = remember { mutableStateOf<String?>(null) }

    LaunchedEffect(bookmarkedImages) {
        val resourceIds = LoadAllPhotosFromLocal(context)
        val bitmaps = resourceIds.mapIndexedNotNull { index, resId ->
            val imageId = "image_$index"
            if (bookmarkedImages.contains(imageId)) {
                imageId to BitmapFactory.decodeResource(context.resources, resId).asImageBitmap()
            } else {
                null
            }
        }
        imageList.value = bitmaps
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        if (imageList.value.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(imageList.value) { (imageId, image) ->
                    Image(
                        bitmap = image,
                        contentDescription = null,
                        modifier = Modifier
                            .width(156.dp)
                            .height(203.dp)
                            .clickable {
                                selectedImage.value = image
                                selectedImageId.value = imageId
                                showDialog.value = true
                            },
                        contentScale = ContentScale.Crop
                    )
                }
            }
        } else {
            Text(text = "북마크한 포즈가 없습니다.")
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
                        contentDescription = null,
                        tint = Color.Red,
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                val newBookmarks = bookmarkedImages.toMutableSet()
                                if (isBookmarked) {
                                    newBookmarks.remove(imageId)
                                    firestore
                                        .collection("users")
                                        .document(auth.currentUser!!.uid)
                                        .collection("bookmarks")
                                        .document(imageId)
                                        .delete()
                                } else {
                                    newBookmarks.add(imageId)
                                    firestore
                                        .collection("users")
                                        .document(auth.currentUser!!.uid)
                                        .collection("bookmarks")
                                        .document(imageId)
                                        .set(mapOf("id" to imageId))
                                }
                                onUpdateBookmarks(newBookmarks)
                            }
                    )
                },
                confirmButton = {
                    IconButton(onClick = { showDialog.value = false }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            tint = Color.Black,
                            contentDescription = "Close"
                        )
                    }
                }
            )
        }
    }
}
