package com.naver.maps.map.compose.Life4cuts.data

import com.naver.maps.geometry.LatLng

data class PhotoBoothInfo(
    val position: LatLng,
    val caption: String,
    val address: String,
    val img: Int,
    val title: String,
    val hashtag: String
)
