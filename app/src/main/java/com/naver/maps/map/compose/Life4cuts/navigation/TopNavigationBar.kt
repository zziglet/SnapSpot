package com.naver.maps.map.compose.Life4cuts.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.naver.maps.map.compose.demo.Life4cuts.NavBarItems
import com.naver.maps.map.compose.demo.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(navController: NavController) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    if (!(currentRoute.equals("Login") || currentRoute.equals("Register"))) {
        if (currentRoute.equals("Album") || currentRoute.equals("MyAlbum") || currentRoute.equals("Bookmark") || currentRoute.equals("Place")) {
            NavigationBar(
                modifier = Modifier
                    .background(color = Color.White)
                    .height(40.dp),
                contentColor = colorResource(id = android.R.color.white),
                containerColor = colorResource(id = android.R.color.white),
            ) {
                NavBarItems.albumtopBarItems.forEach { navItem ->
                    NavigationBarItem(
                        selected = currentRoute == navItem.route,
                        onClick = {
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { /* No icon */ },
                        label = {
                            Text(
                                text = navItem.title,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    lineHeight = 19.6.sp,
                                    fontFamily = FontFamily(Font(R.font.inter)),
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFF000000),
                                    textAlign = TextAlign.Center
                                )
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Red,
                            unselectedIconColor = Color.Blue,
                            selectedTextColor = Color.Black,
                            unselectedTextColor = Color.LightGray,
                            indicatorColor = Color.DarkGray
                        )
                    )
                }
            }
        } else {
            TopAppBar(
                title = {
                    Text(
                        text = "SnapSpot",
                        style = TextStyle(
                            fontSize = 14.sp,
                            lineHeight = 19.6.sp,
                            fontFamily = FontFamily(Font(R.font.inter)),
                            fontWeight = FontWeight(600),
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Center,
                        )
                    )
                },
                modifier = Modifier
                    .background(color = Color.White)
                    .padding(start = 155.dp),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    scrolledContainerColor = Color.White,
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                )
            )
        }
    } else {
        TopAppBar(
            title = { /* Empty title for login/register screen */ },
            modifier = Modifier.background(color = Color.White),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White,
                scrolledContainerColor = Color.White,
                navigationIconContentColor = Color.White,
                titleContentColor = Color.Black,
                actionIconContentColor = Color.Black
            )
        )
    }
}
