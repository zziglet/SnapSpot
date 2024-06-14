package com.naver.maps.map.compose.Life4cuts.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
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

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(navController: NavController) {
    NavigationBar(
        modifier = Modifier
            .background(color = Color.White)
            .height(40.dp),
        contentColor = colorResource(id = android.R.color.white),
        containerColor = colorResource(id = android.R.color.white),
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        if(!(currentRoute.equals("Login") || currentRoute.equals("Register"))){
            if(currentRoute.equals("Album") || currentRoute.equals("MyAlbum") || currentRoute.equals
                    ("Bookmark") || currentRoute.equals("Place")){
                NavBarItems.albumtopBarItems.forEach { navItem ->
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
                        },
                        label = {
                            Text(text = navItem.title,
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    lineHeight = 19.6.sp,
                                    fontFamily = FontFamily(Font(R.font.inter)),
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFF000000),
                                    textAlign = TextAlign.Center
                                ))
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
            }else{
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
                    colors = TopAppBarColors(
                        containerColor = Color.White,
                        scrolledContainerColor = Color.White,
                        navigationIconContentColor = Color.White,
                        titleContentColor = Color.Black,
                        actionIconContentColor = Color.Black
                    )
                )
            }
        }else{
            TopAppBar(
                title = {

                },
                modifier = Modifier.background(color = Color.White),
                colors = TopAppBarColors(
                    containerColor = Color.White,
                    scrolledContainerColor = Color.White,
                    navigationIconContentColor = Color.White,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black
                )
            )
        }
    }
}

