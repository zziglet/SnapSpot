package com.naver.maps.map.compose.Life4cuts.data

import com.naver.maps.geometry.LatLng

data class PhotoBoothInfo(val position: LatLng, val caption: String)

val markers = listOf(
    PhotoBoothInfo(LatLng(37.5409319, 127.0702516), "인생네컷 화양점"),
    PhotoBoothInfo(LatLng(37.5421167, 127.0688291), "돈룩업 건대점"),
    PhotoBoothInfo(LatLng(37.5418542, 127.0683818), "포토이즘 건대점"),
    PhotoBoothInfo(LatLng(37.5430008, 127.0683701), "마달리 스튜디오")
)