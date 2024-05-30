package com.naver.maps.map.compose.demo.Life4cuts

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class BarItem (val title :String, val selectIcon: ImageVector, val onSelectedIcon :ImageVector, val route:String)

object NavBarItems{
    val BarItems = listOf(
        BarItem(
            title = "Map",
            selectIcon = Icons.Default.Map,
            onSelectedIcon = Icons.Outlined.Map,
            route = "Map"
        ),
        BarItem(
            title = "Contacts",
            selectIcon = Icons.Default.Person,
            onSelectedIcon = Icons.Outlined.Person,
            route = "Contacts"
        ),
        BarItem(
            title = "Favorites",
            selectIcon = Icons.Default.Favorite,
            onSelectedIcon = Icons.Outlined.Favorite,
            route = "Favorites"
        )

    )
}