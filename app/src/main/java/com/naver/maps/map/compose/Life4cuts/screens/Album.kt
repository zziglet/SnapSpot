package com.naver.maps.map.compose.Life4cuts.screens

import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import java.io.IOException

@Composable
fun UploadAlbum() {
    val mAuth = FirebaseAuth.getInstance()
    val mStorageRef = FirebaseStorage.getInstance().reference
    val currentUser = mAuth.currentUser

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageBitmap by remember { mutableStateOf<android.graphics.Bitmap?>(null) }
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
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(modifier = Modifier
            .width(327.dp)
            .height(40.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black,
                disabledContainerColor = Color.DarkGray,
                disabledContentColor = Color.White
            ),
            onClick = { launcher.launch("image/*") }) {
            Text(text = "Choose Image")
        }

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
            )
            ,onClick = {
            if (imageUri != null && currentUser != null) {
                val fileReference = mStorageRef.child("uploads/${currentUser.uid}/${System.currentTimeMillis()}.jpg")
                val uploadTask = fileReference.putFile(imageUri!!)
                uploadTask.addOnSuccessListener {
                    Toast.makeText(context, "Upload successful", Toast.LENGTH_LONG).show()
                }.addOnFailureListener { e ->
                    Toast.makeText(context, "Upload failed: ${e.message}", Toast .LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(context, "No image selected or user not logged in", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text = "Upload Image")
        }
    }
}

@Composable
fun AlbumScreen() {
    var showUploadAlbum by remember { mutableStateOf(false) }
    var isFabVisible by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (showUploadAlbum) {
            UploadAlbum()
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
}

@Composable
fun bookmarkPhotoScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Text("bookmarkphoto")
    }

}

@Composable
fun bookmarkPlaceScreen(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){
        Text("bookmarkplace")
    }
}