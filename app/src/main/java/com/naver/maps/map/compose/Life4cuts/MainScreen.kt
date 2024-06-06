package com.naver.maps.map.compose.Life4cuts

import android.view.View
import androidx.annotation.NonNull
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.contentColorFor
import androidx.compose.material3.rememberBottomAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.naver.maps.map.compose.Life4cuts.navigation.BottomNavigationBar
import com.naver.maps.map.compose.Life4cuts.navigation.NagivationHost
import com.naver.maps.map.compose.Life4cuts.navigation.NavRoutes
import com.naver.maps.map.compose.Life4cuts.navigation.TopNavigationBar
import com.naver.maps.map.compose.Life4cuts.screens.LoginScreen
import com.naver.maps.map.compose.demo.R

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(auth: FirebaseAuth) {
    var auth = FirebaseAuth.getInstance()
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
        Column(modifier = Modifier
            .padding(contentPadding)
            .background(color = Color.White)) {
            NagivationHost(navController = navController,auth)
        }
    }
}