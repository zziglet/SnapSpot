package com.naver.maps.map.compose.Life4cuts.screens

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.text.Layout
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
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
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.io.IOException

@Composable
fun UploadAlbum(navController: NavController) {
    val mAuth = FirebaseAuth.getInstance()
    val mStorageRef = FirebaseStorage.getInstance().reference
    val currentUser = mAuth.currentUser

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        imageUri?.let {
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(
                    context.contentResolver,
                    it
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        imageBitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (imageUri == null) {
            Button(
                modifier = Modifier
                    .width(327.dp)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.DarkGray,
                    disabledContentColor = Color.White
                ),
                onClick = { launcher.launch("image/*") }
            ) {
                Text(text = "Choose Image")
            }
        } else {
            Button(
                modifier = Modifier
                    .width(327.dp)
                    .height(40.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.DarkGray,
                    disabledContentColor = Color.White
                ),
                onClick = {
                    if (currentUser != null) {
                        val fileReference = mStorageRef.child("uploads/${currentUser.uid}/${System.currentTimeMillis()}.jpg")
                        val uploadTask = fileReference.putFile(imageUri!!)
                        uploadTask.addOnSuccessListener {
                            Toast.makeText(context, "Upload successful", Toast.LENGTH_LONG).show()
                            navController.navigate("myalbum") // 이전 화면으로 이동
                        }.addOnFailureListener { e ->
                            Toast.makeText(context, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(context, "No image selected or user not logged in", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text(text = "Upload Image")
            }
        }
    }
}

@Composable
fun AlbumScreen(navController: NavController) {
    var showUploadAlbum by remember { mutableStateOf(false) }
    var isFabVisible by remember { mutableStateOf(true) }
    val showDialog = remember { mutableStateOf(false) }
    val user = FirebaseAuth.getInstance().currentUser
    var imageUris by remember { mutableStateOf(listOf<Uri>()) }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }

    LaunchedEffect(user) {
        if (user != null) {
            val storageRef = FirebaseStorage.getInstance().reference.child("uploads/${user.uid}")
            try {
                val result = storageRef.listAll().await()
                val uris = result.items.mapNotNull { it.downloadUrl.await() }
                imageUris = uris
            } catch (e: Exception) {
                Log.e("AlbumScreen", "Error fetching images", e)
            }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(10.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        if (showUploadAlbum) {
            UploadAlbum(navController)
        }else{
            if(imageUris.isEmpty()){
                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "왼쪽 하단의 +를 클릭하여 사진을 추가해보세요!")
                }
            }else{
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(100.dp),
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(imageUris) { url ->
                        Image(
                            painter = rememberAsyncImagePainter(model = url),
                            contentDescription = "",
                            modifier = Modifier
                                .width(156.dp)
                                .height(203.dp)
                                .clickable {
                                    showDialog.value = true
                                    selectedImageUri = url
                                },
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

        if (isFabVisible) {
            FloatingActionButton(
                onClick = {
                    showUploadAlbum = true
                    isFabVisible = false
                },
                containerColor = Color.Black,
                contentColor = Color.White,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Image")
            }
        }
    }
    if (showDialog.value) {
        AlertDialog(
            containerColor = Color.White,
            onDismissRequest = { showDialog.value = false },
            text = {
                Image(
                    painter = rememberAsyncImagePainter(model = selectedImageUri),
                    contentDescription = "",
                    modifier = Modifier.size(500.dp)
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

