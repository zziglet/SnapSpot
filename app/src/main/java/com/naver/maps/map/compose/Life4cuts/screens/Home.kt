package com.naver.maps.map.compose.Life4cuts.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Life4cuts.data.PhotoBoothInfo
import com.naver.maps.map.compose.Life4cuts.data.markers
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.demo.R
import com.naver.maps.map.compose.rememberCameraPositionState


@Composable
@OptIn(ExperimentalNaverMapApi::class)
fun HomeScreen(navController: NavController) {
    val konkuk = LatLng(37.540325, 127.069429)
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        // 카메라 초기 위치를 설정합니다.
        position = CameraPosition(konkuk, 15.0)
    }
    var selectedMarkerInfo by remember { mutableStateOf<PhotoBoothInfo?>(null) }
    Column(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .shadow(12.dp, RectangleShape)
                .border(1.dp, Color(0xFFF7F7F7), RoundedCornerShape(size = 12.dp))
                .width(332.dp)
                .height(171.dp)
                .background(color = Color.White, shape = RoundedCornerShape(size = 12.dp))
                .padding(start = 19.dp, top = 16.dp, end = 16.dp)
                .clickable {

                    if (selectedMarkerInfo != null)
                        navController.navigate(
                            "review/${selectedMarkerInfo?.caption}/${selectedMarkerInfo?.address}" +
                                "/${selectedMarkerInfo?.imgId}/${selectedMarkerInfo?.title}" +
                                "/${selectedMarkerInfo?.hashtag}"
                        )
                },
            verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
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
            Text(
                text = "찍고 싶은 포토부스를 지도에서 선택해줘!",
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
                    painter = painterResource(
                        id = selectedMarkerInfo?.imgId ?: R.drawable.ic_info_black_24dp
                    ),
                    contentDescription = "img",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(70.dp)
                        .height(61.dp)
                )
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.Top),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = selectedMarkerInfo?.caption ?: "Where",
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF000000)
                        )
                    )
                    Text(
                        text = selectedMarkerInfo?.address ?: "서울특별시 광진구 화양동",
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
        Spacer(modifier = Modifier.height(30.dp))
        NaverMap(
            cameraPositionState = cameraPositionState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, top = 0.dp, bottom = 0.dp, end = 20.dp)
        ) {
            markers.forEach { marker ->
                Marker(
                    state = MarkerState(position = marker.position),
                    captionText = marker.caption,
                    captionTextSize = 10.sp,
                    width = 20.dp,
                    height = 30.dp,
                    onClick = {
                        selectedMarkerInfo = marker
                        true
                    }
                )
            }
        }
    }
}