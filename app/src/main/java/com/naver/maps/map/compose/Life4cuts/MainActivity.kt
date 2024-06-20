package com.naver.maps.map.compose.Life4cuts

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.naver.maps.map.compose.demo.NaverMapTheme
import androidx.activity.compose.setContent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : ComponentActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        setContent {
            NaverMapTheme {
                MainScreen(auth,firestore)
            }
        }
    }
}