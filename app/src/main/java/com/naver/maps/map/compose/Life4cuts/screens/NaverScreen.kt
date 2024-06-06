package com.naver.maps.map.compose.Life4cuts.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.clustering.MarkerInfo
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Life4cuts.data.PhotoBoothInfo
import com.naver.maps.map.compose.Life4cuts.data.markers
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.demo.R
import com.naver.maps.map.compose.rememberCameraPositionState

//이 파일 나중에 Home.kt로 옮겨가야 할 것 같아요 근데 무서워서 냅뒀어요

@Composable
@OptIn(ExperimentalNaverMapApi::class)
fun NaverScreen() {

    val konkuk = LatLng(37.540325, 127.069429)
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        // 카메라 초기 위치를 설정합니다.
        position = CameraPosition(konkuk, 15.0)
    }
    var selectedMarkerInfo by remember { mutableStateOf<PhotoBoothInfo?>(null) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Column(
           modifier= Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = selectedMarkerInfo?.title ?: "SnapSpot",
                fontSize = 20.sp
            )
            Text(
                text = selectedMarkerInfo?.hashtag ?: "찍고 싶은 포토부스를 지도에서 선택하세요",
                fontSize = 10.sp
            )
            Row {
                Image(
                    painter = painterResource(
                        id = selectedMarkerInfo?.img ?: R.drawable.ic_info_black_24dp
                    ),
                    contentDescription = "img",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                )
                Column {
                    Text(
                        text = selectedMarkerInfo?.caption ?: "Where",
                        fontSize = 30.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                    Text(
                        text = selectedMarkerInfo?.address ?: "서울특별시 광진구 화양동",
                        fontSize = 10.sp,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        NaverMap(
            cameraPositionState = cameraPositionState,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 0.dp, bottom = 0.dp, end = 10.dp)
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