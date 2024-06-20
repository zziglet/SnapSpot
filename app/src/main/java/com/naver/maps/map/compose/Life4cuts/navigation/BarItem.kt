package com.naver.maps.map.compose.demo.Life4cuts

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BarItem (val title :String, val selectIcon: ImageVector, val onSelectedIcon :ImageVector, val route:String)
data class topBarItem (val title:String, val route:String)
object NavBarItems{
    val BarItems = listOf(
        BarItem(
            title = "Home",
            selectIcon = Icons.Default.Home,
            onSelectedIcon = Icons.Outlined.Home,
            route = "Home"
        ),
        BarItem(
            title = "Photo",
            selectIcon = Icons.Default.Search,
            onSelectedIcon = Icons.Outlined.Search,
            route = "Photo"
        ),
        BarItem(
            title = "Album",
            selectIcon = Icons.Default.CameraAlt,
            onSelectedIcon = Icons.Outlined.CameraAlt,
            route = "Album"
        ),
        BarItem(
            title = "Settings",
            selectIcon = Icons.Default.Settings,
            onSelectedIcon = Icons.Outlined.Settings,
            route = "Settings"
        )
    )
    val albumtopBarItems = listOf(
        topBarItem(
            title = "MyAlbum",
            route = "MyAlbum"
        ),
        topBarItem(
            title = "Place",
            route = "Place"
        ),
        topBarItem(
            title = "Bookmark",
            route = "Bookmark"
        )

    )
}