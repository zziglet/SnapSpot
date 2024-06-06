package com.naver.maps.map.compose.Life4cuts.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
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
    var selectedMarkerInfo by remember { mutableStateOf<PhotoBoothInfo?>(null)}
    Column() {
        Text(
            text = selectedMarkerInfo?.caption ?: "Default text",
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        NaverMap(
            cameraPositionState = cameraPositionState,
            modifier = Modifier.fillMaxWidth()
        ) {
            markers.forEach { marker ->
                Marker(
                    state = MarkerState(position = marker.position),
                    captionText = marker.caption,
                    captionTextSize = 10.sp,
                    width = 20.dp,
                    height = 30.dp,
                    onClick ={
                        selectedMarkerInfo = marker
                        true
                    }
                )
            }
        }
    }
}