package com.naver.maps.map.compose.Life4cuts.screens

import ReviewViewModel
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.naver.maps.map.compose.Life4cuts.data.PhotoBoothInfo
import com.naver.maps.map.compose.Life4cuts.navigation.NavRoutes
import com.naver.maps.map.compose.demo.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    caption: String,
    address: String,
    imgId: Int,
    title: String,
    hashtag: String,
    reviewViewModel: ReviewViewModel,
    navController: NavController,
    firestore: FirebaseFirestore
) {
    val userId = reviewViewModel.userId?:""

    LaunchedEffect(userId) {
        if (userId.isEmpty()) {
            Log.d("UserIdCheck", "UserId is null or blank")
            navController.navigate(NavRoutes.Login.route)
        } else {
            Log.d("UserIdCheck", "UserId: $userId")
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item { ShowPhotoBooth(userId, caption, address, imgId, title, hashtag, firestore) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { ShowPhotoexample(title) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { ShowReviewList(reviewViewModel, title) }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { ReviewInput(reviewViewModel, title) }
    }
}

@Composable
fun ShowPhotoBooth(
    userId: String,
    caption: String,
    address: String,
    imgId: Int,
    title: String,
    hashtag: String,
    firestore: FirebaseFirestore
) {
    var isFavorite by remember { mutableStateOf(false) }

    // Firestore에서 즐겨찾기 상태를 불러오는 함수
    LaunchedEffect(userId, title) {
        firestore.collection("users")
            .document(userId)
            .collection("favorites")
            .document(title) // title을 문서 ID로 사용
            .get()
            .addOnSuccessListener { document ->
                isFavorite = document.exists()
            }
    }

    Column(
        modifier = Modifier
            .shadow(12.dp, RectangleShape)
            .border(1.dp, Color(0xFFF7F7F7), RoundedCornerShape(size = 12.dp))
            .width(332.dp)
            .height(171.dp)
            .background(color = Color.White, shape = RoundedCornerShape(size = 12.dp))
            .padding(start = 19.dp, top = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Top),
        horizontalAlignment = Alignment.Start
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SnapSpot",
                style = TextStyle(
                    fontSize = 20.sp,
                    lineHeight = 30.sp,
                    fontFamily = FontFamily(Font(R.font.judson)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),
                )
            )
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite Icon",
                tint = Color.Red,
                modifier = Modifier
                    .size(24.dp)
                    .clickable {
                        isFavorite = !isFavorite
                        if (isFavorite) {
                            // Firestore에 즐겨찾기 추가
                            firestore.collection("users")
                                .document(userId)
                                .collection("favorites")
                                .document(title)
                                .set(mapOf(
                                    "caption" to caption,
                                    "address" to address,
                                    "imgId" to imgId,
                                    "title" to title,
                                    "hashtag" to hashtag
                                ))
                        } else {
                            // Firestore에서 즐겨찾기 삭제
                            firestore.collection("users")
                                .document(userId)
                                .collection("favorites")
                                .document(title)
                                .delete()
                        }
                    }
            )
        }
        Text(
            text = hashtag,
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 18.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(500),
                color = Color(0xFF828282),
            )
        )
        Row(
            modifier = Modifier
                .width(230.dp)
                .height(100.dp),
            horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imgId),
                contentDescription = "img",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(70.dp)
                    .height(61.dp)
            )
            Column(modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.Top),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = caption,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF000000)
                    )
                )
                Text(
                    text = address,
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 18.sp,
                        fontFamily = FontFamily(Font(R.font.inter)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF828282),
                    )

                )
            }
        }
    }
}


@Composable
fun ShowPhotoexample(title: String) {
    var selectedImageResId by remember { mutableStateOf<Int?>(null) }
    val imageResources = getImageResources(title) // title에 해당하는 이미지 리소스를 가져옴

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = "How", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        LazyRow(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            items(imageResources) { imageResId ->
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 8.dp)
                        .clickable { selectedImageResId = imageResId }
                ) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }

    if (selectedImageResId != null) {
        AlertDialog(
            onDismissRequest = { selectedImageResId = null },
            confirmButton = {
                IconButton(onClick = { selectedImageResId = null }) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            },
            text = {
                Image(
                    painter = painterResource(id = selectedImageResId!!),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp) // 높이를 적절하게 조정하세요
                )
            }
        )
    }
}

@Composable
fun getImageResources(title: String): List<Int> {
    val context = LocalContext.current
    val imageResources = mutableListOf<Int>()
    var index = 0

    while (true) {
        val imageName = "pic_${title}$index"
        val imageResId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
        if (imageResId != 0) {
            imageResources.add(imageResId)
            index++
        } else {
            break
        }
    }
    return imageResources
}

@Composable
fun ShowReviewList(reviewViewModel: ReviewViewModel, title: String) {
    val reviews = reviewViewModel.reviews

    LaunchedEffect(title) {
        reviewViewModel.loadReviews(title)
    }
    Column(
        modifier = Modifier
            .height(250.dp) // LazyColumn의 높이 설정
    ) {
        Text(text = "Review", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(items = reviews) { review ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = review)
                    }
                }
            }
        }
    }
}

@Composable
fun ReviewInput(reviewViewModel: ReviewViewModel, title: String) {
    var reviewText by remember { mutableStateOf(TextFieldValue("")) }
    var rating by remember { mutableStateOf(0) }

    Column {
        Text("Rate the place:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        Row {
            for (i in 1..5) {
                Icon(
                    imageVector = if (i <= rating) Icons.Filled.Star else Icons.Filled.StarBorder,
                    contentDescription = "Star Rating",
                    tint = Color.Yellow,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(4.dp)
                        .clickable { rating = i }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextField(
            value = reviewText,
            onValueChange = { reviewText = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Write a review") }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (reviewText.text.isNotEmpty() && rating > 0) {
                    reviewViewModel.addReview(title, "⭐ $rating ${reviewText.text}")
                    reviewText = TextFieldValue("") // Clear the text field
                    rating = 0 // Reset rating
                }
            },
            modifier = Modifier.align(Alignment.End)
        ) {
            Text("Submit")
        }
    }
}