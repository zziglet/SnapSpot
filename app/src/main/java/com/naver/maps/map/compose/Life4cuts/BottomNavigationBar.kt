package com.naver.maps.map.compose.demo.Life4cuts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BottomNavigationBar(navController: NavController) {

    NavigationBar(
        modifier = Modifier
            .padding(start = 20.dp)
            .padding(end = 20.dp)
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        NavBarItems.BarItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route)
                    {
                        popUpTo(navController.graph.findStartDestination().id) {
                        }
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (currentRoute == navItem.route)
                            navItem.onSelectedIcon else navItem.selectIcon,
                        contentDescription = navItem.title
                    )
                },
                label = {
                    Text(text = navItem.title)
                }
            )
        }
    }
}

