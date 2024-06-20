package com.naver.maps.map.compose.Life4cuts.navigation

import androidx.compose.foundation.background
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.naver.maps.map.compose.demo.Life4cuts.NavBarItems

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        modifier = Modifier
            .background(color = Color.White),
        contentColor = colorResource(id = android.R.color.white),
        containerColor = colorResource(id = android.R.color.white),
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        if(!(currentRoute.equals("Login") || currentRoute.equals("Register"))){
                NavBarItems.BarItems.forEach { navItem ->
                    NavigationBarItem(
                        modifier = Modifier.background(color = Color.White),
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
                        },
                        colors = NavigationBarItemColors(
                            selectedIconColor = Color.Black,
                            unselectedIconColor = Color.LightGray,
                            disabledIconColor = Color.LightGray,
                            selectedIndicatorColor = Color.White,
                            disabledTextColor = Color.LightGray,
                            unselectedTextColor = Color.LightGray,
                            selectedTextColor = Color.Black
                        )
                    )
                }
        }
    }
}

