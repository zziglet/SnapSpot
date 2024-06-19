package com.naver.maps.map.compose.Life4cuts.screens

import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import com.naver.maps.map.compose.Life4cuts.data.ImageInfo
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.naver.maps.map.compose.demo.R
import kotlinx.coroutines.tasks.await
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//Album 화면 : 사용자가 사진을 등록하는 함수
@Composable
fun UploadAlbum(navController: NavController) {
    val mAuth = FirebaseAuth.getInstance()
    val mStorageRef = FirebaseStorage.getInstance().reference
    val currentUser = mAuth.currentUser
    val db = FirebaseFirestore.getInstance()

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var description by remember { mutableStateOf("") }
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
            BasicTextField(
                modifier = Modifier
                    .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(7.dp))
                    .width(327.dp)
                    .height(40.dp)
                    .background(Color.White),
                value = description,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,  // 기본 텍스트 입력 타입 설정
                    imeAction = ImeAction.Done         // IME 액션 설정 (예: 완료)
                ),
                onValueChange = { description = it},
                decorationBox = { innerTextField ->
                    Row(
                        modifier = Modifier.padding(start = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (description.equals("")) "Enter description" else "",
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 19.6.sp,
                                fontFamily = FontFamily(Font(R.font.inter)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF828282)
                            )
                        )
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        innerTextField()
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

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
                    if (currentUser != null && imageUri != null) {
                        val fileReference = mStorageRef.child("uploads/${currentUser.uid}/${System.currentTimeMillis()}.jpg")
                        val uploadTask = fileReference.putFile(imageUri!!)
                        uploadTask.addOnSuccessListener {
                            fileReference.downloadUrl.addOnSuccessListener { url ->
                                val data = hashMapOf(
                                    "imageUrl" to url.toString(),
                                    "description" to description,
                                    "userId" to currentUser.uid,
                                    "timestamp" to System.currentTimeMillis()
                                )
                                db.collection("uploads").add(data)
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Upload successful", Toast.LENGTH_LONG).show()
                                        navController.navigate("myalbum")
                                    }
                                    .addOnFailureListener { e ->
                                        Toast.makeText(context, "Failed to save data: ${e.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }
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

//Album 화면 : 사용자가 등록한 앨범 출력 함수
@Composable
fun AlbumScreen(navController: NavController) {
    var showUploadAlbum by remember { mutableStateOf(false) }
    var isFabVisible by remember { mutableStateOf(true) }
    val showDialog = remember { mutableStateOf(false) }
    val user = FirebaseAuth.getInstance().currentUser
    var imageInfos by remember { mutableStateOf(listOf<ImageInfo>()) }
    var selectedImageInfo by remember { mutableStateOf<ImageInfo?>(null) }
    val context = LocalContext.current

    LaunchedEffect(user) {
        if (user != null) {
            val db = FirebaseFirestore.getInstance()
            try {
                val result = db.collection("uploads").get().await()
                val infos = result.documents.mapNotNull { document ->
                    val data = document.data
                    val imageUrl = data?.get("imageUrl") as? String
                    val text = data?.get("description") as? String ?: ""
                    val userId = data?.get("userId") as? String ?: ""
                    val timestamp = data?.get("timestamp") as? Long ?: 0L

                    if (imageUrl != null && userId == user.uid) {
                        ImageInfo(imageUrl, text, timestamp)
                    } else {
                        null
                    }
                }
                imageInfos = infos
            } catch (e: Exception) {
                Log.e("AlbumScreen", "Error fetching images", e)
                Toast.makeText(context, "Error fetching images: ${e.message}", Toast.LENGTH_SHORT).show()
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
        } else {
            if (imageInfos.isEmpty()) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "왼쪽 하단의 +를 클릭하여 사진을 추가해보세요!")
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(100.dp),
                    contentPadding = PaddingValues(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(imageInfos) { info ->
                        Column(
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = info.uri),
                                contentDescription = "",
                                modifier = Modifier
                                    .width(187.2.dp)
                                    .height(243.6.dp)
                                    .clickable {
                                        showDialog.value = true
                                        selectedImageInfo = info
                                    },
                                contentScale = ContentScale.Crop
                            )
                            Text(text = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date
                                (info.timestamp)),
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    lineHeight = 19.5.sp,
                                    fontFamily = FontFamily(Font(R.font.inter)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF000000)
                                ))
                            Text(text = info.text,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    lineHeight = 19.6.sp,
                                    fontFamily = FontFamily(Font(R.font.inter)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0x80000000),
                                ))
                        }
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(model = selectedImageInfo?.uri),
                        contentDescription = "",
                        modifier = Modifier.size(500.dp)
                    )
                }
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



