import android.annotation.SuppressLint
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.naver.maps.map.compose.Life4cuts.navigation.NavRoutes
import com.naver.maps.map.compose.Life4cuts.viewModel.FavoriteViewModel
import com.naver.maps.map.compose.demo.R
import kotlinx.coroutines.tasks.await


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
            Text(
                text = "SnapSpot",
                style = TextStyle(
                    fontSize = 40.sp,
                    lineHeight = 56.sp,
                    fontFamily = FontFamily(Font(R.font.judson)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF000000),
                    textAlign = TextAlign.Center,
                )
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                SettingsItem(
                    icon = painterResource(id = R.drawable.baseline_person_24),
                    text = "별명 수정",
                    onClick = { navController.navigate("profile") }
                )
            }

            //place 초기화
            item {
                SettingsItem(
                    icon = painterResource(id = R.drawable.baseline_lock_24),
                    text = "즐겨찾기한 장소 초기화",
                    onClick = { navController.navigate("public") }
                )
            }

            //bookmark 초기화
            item {
                SettingsItem(
                    icon = painterResource(id = R.drawable.baseline_star_24),
                    text = " 북마크한 사진 초기화",
                    onClick = { navController.navigate("favorites") }
                )
            }
            item {
                SettingsItem(
                    icon = painterResource(id = R.drawable.baseline_settings_24),
                    text = "로그아웃",
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
        Text(
            text = text,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 19.6.sp,
                fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),

                )
        )

    }
}


//
@Composable
fun ProfileScreen(navController: NavController, auth: FirebaseAuth, firestore: FirebaseFirestore) {
    val context = LocalContext.current
    val currentUser = auth.currentUser
    var nickname by remember { mutableStateOf("Loading...") }
    var showDialog by remember { mutableStateOf(false) }

    // Firestore에서 별명 가져오기
    LaunchedEffect(currentUser) {
        if (currentUser != null) {
            try {
                val docRef = firestore.collection("users").document(currentUser.uid)
                val snapshot = docRef.get().await()
                nickname = snapshot.getString("nickname") ?: "별명을 지어주세요 !"
            } catch (e: Exception) {
                nickname = "별명을 불러오는 중 오류가 발생했습니다."
            }
        } else {
            nickname = "로그인이 필요합니다."
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = nickname)
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { showDialog = true }) {
            Text(text = "별명 등록/수정하기")
        }
    }

    if (showDialog) {
        val builder = AlertDialog.Builder(context)
        val input = EditText(context)
        builder.setView(input)
        builder.setPositiveButton("확인") { dialog, _ ->
            val newNickname = input.text.toString()
            currentUser?.let {
                firestore.collection("users").document(it.uid).set(mapOf("nickname" to newNickname))
                nickname = newNickname
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("취소") { dialog, _ -> dialog.cancel() }
        builder.show()
        showDialog = false
    }
}
//

//장소 즐겨찾기 초기화
@Composable
fun PublicSettingScreen(navController: NavController, auth: FirebaseAuth, firestore: FirebaseFirestore, favoriteViewModel: FavoriteViewModel) {
    var showClearDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { showClearDialog = true }) {
            Text(text = "즐겨찾기 초기화")
        }

        if (showClearDialog) {
            ClearFavoritesConfirmationDialog(
                onConfirm = {
                    clearAllFavorites(auth, firestore, favoriteViewModel)
                    showClearDialog = false
                },
                onDismiss = { showClearDialog = false }
            )
        }
    }
}

@Composable
fun ClearFavoritesConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    val context = LocalContext.current

    AlertDialog.Builder(context).apply {
        setTitle("즐겨찾기 초기화")
        setMessage("즐겨찾기한 장소를 초기화 하시겠습니까?")
        setPositiveButton("예") { dialog, _ ->
            onConfirm()
            dialog.dismiss()
        }
        setNegativeButton("아니오") { dialog, _ ->
            onDismiss()
            dialog.dismiss()
        }
        create()
        show()
    }
}

private fun clearAllFavorites(auth: FirebaseAuth, firestore: FirebaseFirestore, favoriteViewModel: FavoriteViewModel) {
    val currentUser = auth.currentUser ?: return
    firestore.collection("users").document(currentUser.uid).collection("favorites")
        .get()
        .addOnSuccessListener { snapshot ->
            val batch = firestore.batch()
            for (document in snapshot.documents) {
                batch.delete(document.reference)
            }
            batch.commit().addOnSuccessListener {
                favoriteViewModel.favorites.clear()
            }
        }
}

//사진 즐겨찾기 초기화
@Composable
fun FavoritesScreen(navController: NavController, auth: FirebaseAuth, firestore: FirebaseFirestore, onClearBookmarks: () -> Unit) {
    var showClearDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { showClearDialog = true }) {
            Text(text = "북마크 초기화")
        }

        if (showClearDialog) {
            ClearBookmarksConfirmationDialog(
                onConfirm = {
                    clearAllBookmarks(auth, firestore, onClearBookmarks)
                    showClearDialog = false
                },
                onDismiss = { showClearDialog = false }
            )
        }
    }
}

@Composable
fun ClearBookmarksConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    val context = LocalContext.current

    AlertDialog.Builder(context).apply {
        setTitle("북마크 초기화")
        setMessage("북마크한 사진을 초기화 하시겠습니까?")
        setPositiveButton("예") { dialog, _ ->
            onConfirm()
            dialog.dismiss()
        }
        setNegativeButton("아니오") { dialog, _ ->
            onDismiss()
            dialog.dismiss()
        }
        create()
        show()
    }
}

private fun clearAllBookmarks(auth: FirebaseAuth, firestore: FirebaseFirestore, onClearBookmarks: () -> Unit) {
    val currentUser = auth.currentUser ?: return
    firestore.collection("users").document(currentUser.uid).collection("bookmarks")
        .get()
        .addOnSuccessListener { snapshot ->
            val batch = firestore.batch()
            for (document in snapshot.documents) {
                batch.delete(document.reference)
            }
            batch.commit().addOnSuccessListener {
                onClearBookmarks()
            }
        }
}

//


@Composable
fun AccountSettingsScreen(navController: NavController, auth: FirebaseAuth) {
    var showLogoutDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = { showLogoutDialog = true }) {
            Text(text = "로그아웃")
        }

        if (showLogoutDialog) {
            LogoutConfirmationDialog(
                onConfirm = {
                    auth.signOut()
                    navController.navigate(NavRoutes.Login.route) {
                        popUpTo(NavRoutes.Login.route) { inclusive = true }
                    }
                    showLogoutDialog = false
                },
                onDismiss = { showLogoutDialog = false }
            )
        }
    }
}

//로그아웃 팝업
@Composable
fun LogoutConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    val context = LocalContext.current

    AlertDialog.Builder(context).apply {
        setTitle("로그아웃")
        setMessage("로그아웃 하시겠습니까?")
        setPositiveButton("예") { dialog, _ ->
            onConfirm()
            dialog.dismiss()
        }
        setNegativeButton("아니오") { dialog, _ ->
            onDismiss()
            dialog.dismiss()
        }
        create()
        show()
    }
}