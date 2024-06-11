package com.naver.maps.map.compose.Life4cuts.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

@Composable
fun PhotoScreen(auth:FirebaseAuth,firestore: FirebaseFirestore) {
    // 임시 코드입니다
    val currentUser = auth.currentUser
    currentUser?.let {
        UserDataScreen(it.uid, firestore)
    } ?: run {
        Text(text = "로그인되지 않았습니다.")
    }

//    Box(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Text(text = "photo main")
//
//    }
}

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
