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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.FirebaseFirestore
import com.naver.maps.map.compose.Life4cuts.navigation.NavRoutes
import com.naver.maps.map.compose.demo.R

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
                text = title,
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
                            firestore
                                .collection("users")
                                .document(userId)
                                .collection("favorites")
                                .document(title)
                                .set(
                                    mapOf(
                                        "caption" to caption,
                                        "address" to address,
                                        "imgId" to imgId,
                                        "title" to title,
                                        "hashtag" to hashtag
                                    )
                                )
                        } else {
                            // Firestore에서 즐겨찾기 삭제
                            firestore
                                .collection("users")
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
            modifier = Modifier.fillMaxWidth(),
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
                .width(332.dp)
                .height(130.dp),
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
    val imageResources = GetImageResources(title) // title에 해당하는 이미지 리소스를 가져옴

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = "How",
            style = TextStyle(
                fontSize = 18.sp,
                lineHeight = 21.6.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFF000000),

                ))
        LazyRow(
            modifier = Modifier.padding(top = 8.dp)
        ) {
            items(imageResources) { imageResId ->
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .size(134.dp)
                        .padding(end = 8.dp)
                        .clickable { selectedImageResId = imageResId }
                ) {
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = null,
                        modifier = Modifier
                            .width(122.dp)
                            .height(134.dp),
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
                        .height(400.dp)
                )
            }
        )
    }
}

@Composable
fun GetImageResources(title: String): List<Int> {
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
    var showReviewinput by remember { mutableStateOf(false) }
    var reviewText by remember { mutableStateOf(TextFieldValue("")) }
    var rating by remember { mutableStateOf(0) }
    var confirmbtnText by remember { mutableStateOf("Close") }

    LaunchedEffect(title) {
        reviewViewModel.loadReviews(title)
    }
    Column(
        modifier = Modifier
            .height(310.dp) // LazyColumn의 높이 설정
    ) {
        Row() {
            Text(text = "Review",
                style = TextStyle(
                    fontSize = 18.sp,
                    lineHeight = 21.6.sp,
                    fontFamily = FontFamily(Font(R.font.inter)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000)))
            Spacer(modifier = Modifier.width(290.dp))
            FloatingActionButton(
                onClick = {
                    showReviewinput = true
                    reviewText = TextFieldValue("")
                },
                containerColor = Color.White,
                contentColor = Color.Black,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Image")
            }
        }
        Spacer(modifier = Modifier.padding(5.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            if(reviews.isEmpty()){
                item {
                    Column {
                        Text(text = "등록된 리뷰가 없습니다.")
                    }
                }
            }else{
                items(items = reviews) { review ->
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    color = Color(0xFFFFFFFF),
                                    shape = RoundedCornerShape(size = 12.dp)
                                )
                                .padding(start = 19.dp, top = 10.dp, end = 4.dp, bottom = 5.dp)
                                .shadow(
                                    elevation = 20.dp, spotColor = Color(0x1A000000),
                                    ambientColor = Color(0x1A000000)
                                )
                                .width(332.dp)
                                .height(50.dp)
                        ) {
                            Text(text = review.first,
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    lineHeight = 18.sp,
                                    fontFamily = FontFamily(Font(R.font.inter)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFF000000)
                                    ))  // 별점
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = review.second,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    lineHeight = 19.5.sp,
                                    fontFamily = FontFamily(Font(R.font.inter)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF000000)
                                    )) // 리뷰 텍스트
                        }
                    }
                }
            }
        }
    }
    if(showReviewinput){
        AlertDialog(
            onDismissRequest = { reviewText = TextFieldValue("")
                rating = 0
                confirmbtnText = "Close"},
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White,
                        disabledContainerColor = Color.DarkGray,
                        disabledContentColor = Color.White
                    ),
                    shape = RoundedCornerShape(12.dp),
                    onClick = {
                        if (reviewText.text.isNotEmpty() && rating > 0) {
                            reviewViewModel.addReview(title, "⭐ $rating\n ${reviewText.text}")
                            reviewText = TextFieldValue("") // Clear the text field
                            rating = 0 // Reset rating
                            showReviewinput = false
                            confirmbtnText = "Close"
                        }else if(reviewText.text.isEmpty()){
                            confirmbtnText = "Close"
                        }
                        showReviewinput = false
                    }
                ) {
                    Text(confirmbtnText,
                        style = TextStyle(
                            fontSize = 13.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF)
                        )
                    )
                }
            }
            ,text = {
                Column (
                ){
                    Text("Write Review",
                        style = TextStyle(
                            fontSize = 18.sp,
                            lineHeight = 21.6.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFF000000)))
                    Spacer(modifier = Modifier.height(15.dp))
                    Row {
                        for (i in 1..5) {
                            Icon(
                                imageVector = if (i <= rating) Icons.Filled.Star else Icons.Filled.StarBorder,
                                contentDescription = "Star Rating",
                                tint = Color(0xFFF1C249),
                                modifier = Modifier
                                    .size(32.dp)
                                    .clickable { rating = i }
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    BasicTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(25.dp)
                            .border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(7.dp)
                            )
                            .background(Color.White),
                        value = reviewText,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,  // 기본 텍스트 입력 타입 설정
                            imeAction = ImeAction.Done         // IME 액션 설정 (예: 완료)
                        ),
                        onValueChange = { reviewText = it
                                        if(reviewText.equals("")){
                                            confirmbtnText = "Close"
                                        }else{
                                            confirmbtnText = "Submit"
                                        }},
                        decorationBox = { innerTextField ->
                            Row(
                                modifier = Modifier.padding(start = 5.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = if (reviewText.equals(null)) "Write a review" else
                                            "",
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
                }
                
            }
        )
    }
}