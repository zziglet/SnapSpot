package com.naver.maps.map.compose.Life4cuts.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naver.maps.map.compose.demo.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewPage() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("SnapSpot") }) },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item { Spacer(modifier = Modifier.height(56.dp)) }
            item { ShowPhotoexample() }
            item { ShowReview() }
        }
    }
}

@Composable
fun ShowPhotoexample() {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = "How", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        LazyRow(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            items(5) { // replace with actual item count
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 8.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img4), // replace with actual image resource
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

@Composable
fun ShowReview() {
    Column {
        Text(text = "Review", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) ,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "⭐ 3.5 주말에 사람이 많아요")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "⭐ 4.2 머리띠만 선글라스 종류가 엄청 다양해요!")
            }
        }
    }
}