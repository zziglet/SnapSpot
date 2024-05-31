package com.naver.maps.map.compose.Life4cuts.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState

data class MarkerInfo(val position: LatLng, val caption: String)

val markers = listOf(
    MarkerInfo(LatLng(37.5409319, 127.0702516), "인생네컷 화양점"),
    MarkerInfo(LatLng(37.5421167, 127.0688291), "돈룩업 건대점"),
    MarkerInfo(LatLng(37.5418542, 127.0683818), "포토이즘 건대점"),
    MarkerInfo(LatLng(37.5430008, 127.0683701), "마달리 스튜디오")
)

@Composable
@OptIn(ExperimentalNaverMapApi::class)
fun NaverScreen() {

    val konkuk = LatLng(37.540325, 127.069429)
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        // 카메라 초기 위치를 설정합니다.
        position = CameraPosition(konkuk, 15.0)
    }
    Column() {
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
                    height = 30.dp
                )
            }
        }
    }
}