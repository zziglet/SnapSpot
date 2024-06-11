package com.naver.maps.map.compose.Life4cuts.data

import com.naver.maps.geometry.LatLng
import com.naver.maps.map.compose.demo.R

val markers = listOf(
    PhotoBoothInfo(
        position = LatLng(37.5409319, 127.0702516),
        caption = "인생네컷 화양점",
        address = "서울특별시 광진구 화양동 번지 1층 5-72 2층",
        img = R.drawable.life4cuts,
        title = "인생네컷",
        hashtag = "#PhotoBooth.kt에서 수정가능",
    ),
    PhotoBoothInfo(
        position = LatLng(37.5421167, 127.0688291),
        caption = "돈룩업 건대점",
        address = "서울특별시 광진구 아차산로29길 28",
        img = R.drawable.dontlookup,
        title = "돈룩업",
        hashtag = "#PhotoBooth.kt에서 수정가능",

    ),
    PhotoBoothInfo(
        position = LatLng(37.5418542, 127.0683818),
        caption = "포토이즘 건대점",
        address = "서울특별시 광진구 아차산로29길 19 2층 포토이즘 건대점",
        img = R.drawable.photoism,
        title = "포토이즘",
        hashtag = "#PhotoBooth.kt에서 수정가능",
    ),
    PhotoBoothInfo(
        position = LatLng(37.5430008, 127.0683701),
        caption = "마달리 스튜디오 건대점",
        address = "서울특별시 광진구 동일로24길 60 B1",
        img = R.drawable.photoism,
        title = "마달리 스튜디오",
        hashtag = "#PhotoBooth.kt에서 수정가능",
    )
)

