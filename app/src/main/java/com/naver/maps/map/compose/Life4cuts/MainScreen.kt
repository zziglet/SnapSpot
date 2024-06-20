package com.naver.maps.map.compose.Life4cuts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.naver.maps.map.compose.Life4cuts.navigation.BottomNavigationBar
import com.naver.maps.map.compose.Life4cuts.navigation.NavigationHost
import com.naver.maps.map.compose.Life4cuts.navigation.TopNavigationBar

@Composable
fun MainScreen(auth: FirebaseAuth, firestore: FirebaseFirestore) {

    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier.background(color = Color.White),
        topBar = {
            TopNavigationBar(navController)
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .padding(contentPadding)
                .background(color = Color.White)
        ) {
            //NavigationHost 오타 수정
            NavigationHost(navController = navController, auth, firestore)
        }
    }
}