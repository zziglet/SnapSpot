import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
//import com.example.team04.R
import com.naver.maps.map.compose.demo.R


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp, bottom = 80.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("SnapSpot", fontSize = 45.sp, fontWeight = FontWeight.Bold)
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                SettingsItem(
                    icon = painterResource(id = R.drawable.baseline_person_24),
                    text = "사용자 프로필 수정",
                    onClick = { navController.navigate("profile") }
                )
            }
            item {
                SettingsItem(
                    icon = painterResource(id = R.drawable.baseline_lock_24),
                    text = "공개 설정",
                    onClick = { navController.navigate("public") }
                )
            }
            item {
                SettingsItem(
                    icon = painterResource(id = R.drawable.baseline_star_24),
                    text = "즐겨찾기 설정",
                    onClick = { navController.navigate("favorites") }
                )
            }
            item {
                SettingsItem(
                    icon = painterResource(id = R.drawable.baseline_settings_24),
                    text = "계정 설정",
                    onClick = { navController.navigate("account") }
                )
            }
        }
    }
}

@Composable
fun SettingsItem(icon: Painter, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = icon,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .padding(end = 16.dp)
        )
        Text(text = text, fontSize = 22.sp)

    }
}

@Composable
fun ProfileScreen(navController: NavController) {
    //
}


@Composable
fun PublicSettingScreen(navController: NavController) {
    //
}



@Composable
fun FavoritesScreen(navController: NavController) {
    //
}


@Composable
fun AccountSettingsScreen(navController: NavController) {
    //
}