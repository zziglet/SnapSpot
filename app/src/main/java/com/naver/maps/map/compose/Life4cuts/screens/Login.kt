package com.naver.maps.map.compose.Life4cuts.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TextFieldDefaults.indicatorLine
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.naver.maps.map.compose.Life4cuts.navigation.NavRoutes
import com.naver.maps.map.compose.demo.R


@Composable
fun LoginScreen(navController: NavController, auth: FirebaseAuth) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .background(color = Color(0xFFFFFFFF))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "나만의 포토부스 관리 서비스",
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 18.sp,
                //fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = Color(0xFF828282),
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = "SnapSpot",
            style = TextStyle(
                fontSize = 48.sp,
                lineHeight = 72.sp,
                //fontFamily = FontFamily(Font(R.font.judson)),
                fontWeight = FontWeight(400),
                fontStyle = FontStyle.Italic,
                color = Color(0xFF000000),
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.height(30.dp))
        Image(
            modifier = Modifier.height(90.dp),
            painter = painterResource(id = R.drawable.loginimg),
            contentDescription = "image description",
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Create an account",
            style = TextStyle(
                fontSize = 18.sp,
                lineHeight = 27.sp,
                //fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = "Enter your email to sign up for this app",
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 21.sp,
                //fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.height(40.dp))
        BasicTextField(
            modifier = Modifier
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(7.dp))
                .width(300.dp)
                .height(27.dp)
                .background(Color.White),
            value = email,
            onValueChange = { email = it },
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.padding(start = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (email.isEmpty()) "email@domain.com" else "",
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 19.6.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF828282),
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    innerTextField()
                }
            }
        )
        Spacer(modifier = Modifier.height(12.dp))
        BasicTextField(
            modifier = Modifier
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(7.dp))
                .width(300.dp)
                .height(27.dp)
                .background(Color.White),
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.padding(start = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (password.isEmpty()) "password" else "",
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 19.6.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF828282),
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    innerTextField()
                }
            }
        )
        Spacer(modifier = Modifier.height(40.dp))
        Button(
            modifier = Modifier.width(300.dp),
            onClick = {
                loginUser(email, password, auth, navController) { error ->
                    errorMessage = error
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White,
                disabledContainerColor = Color.DarkGray,
                disabledContentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Sign In")
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "or the account already",
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 19.6.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF828282),
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier.width(300.dp),
            onClick = { navController.navigate("register") },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black,
                disabledContainerColor = Color.DarkGray,
                disabledContentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Sign Up")
        }
    }
}

private fun loginUser(
    email: String,
    password: String,
    auth: FirebaseAuth,
    navController: NavController,
    onError: (String) -> Unit,
) {
    auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Login successful
                navController.navigate(NavRoutes.Home.route)
            } else {
                // Login failed
                onError("이메일 또는 비밀번호를 확인하세요")
            }
        }
}

@Composable
fun RegisterScreen(navController: NavController, auth: FirebaseAuth) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember {
        mutableStateOf<String?>(null)
    }
    var passwordError by remember {
        mutableStateOf<String?>(null)
    }

    fun validateInput() {
        emailError = if (android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            null
        } else {
            "유효한 이메일을 입력하세요."
        }

        passwordError = if (password.length >= 6) {
            null
        } else {
            "비밀번호는 6자 이상이어야 합니다."
        }
    }

    Column(
        modifier = Modifier
            .background(color = Color(0xFFFFFFFF))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "나만의 포토부스 관리 서비스",
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 18.sp,
                //fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = Color(0xFF828282),
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = "SnapSpot",
            style = TextStyle(
                fontSize = 48.sp,
                lineHeight = 72.sp,
                //fontFamily = FontFamily(Font(R.font.judson)),
                fontWeight = FontWeight(400),
                fontStyle = FontStyle.Italic,
                color = Color(0xFF000000),
                textAlign = TextAlign.Center
            )
        )
        Spacer(modifier = Modifier.height(30.dp))
        Image(
            modifier = Modifier.height(90.dp),
            painter = painterResource(id = R.drawable.loginimg),
            contentDescription = "image description",
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = "Create an account",
            style = TextStyle(
                fontSize = 18.sp,
                lineHeight = 27.sp,
                //fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(600),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center
            )
        )
        Text(
            text = "Enter your email to sign up for this app",
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 21.sp,
                //fontFamily = FontFamily(Font(R.font.inter)),
                fontWeight = FontWeight(400),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center
            )
        )

        Spacer(modifier = Modifier.height(40.dp))
        BasicTextField(
            modifier = Modifier
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(7.dp))
                .width(300.dp)
                .height(27.dp)
                .background(Color.White),
            value = email,
            onValueChange = { email = it },
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.padding(start = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (email.isEmpty()) "email@domain.com" else "",
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 19.6.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF828282),
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    innerTextField()
                }
            }
        )
        if (emailError != null) {
            Text(
                text = emailError!!,
                color = Color.Red,
                style = TextStyle(fontSize = 12.sp)
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        BasicTextField(
            modifier = Modifier
                .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(7.dp))
                .width(300.dp)
                .height(27.dp)
                .background(Color.White),
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            decorationBox = { innerTextField ->
                Row(
                    modifier = Modifier.padding(start = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (password.isEmpty()) "password" else "",
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 19.6.sp,
                            fontWeight = FontWeight(400),
                            color = Color(0xFF828282),
                            textAlign = TextAlign.Center
                        )
                    )
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    innerTextField()
                }
            }
        )
        if (passwordError != null) {
            Text(
                text = passwordError!!,
                color = Color.Red,
                style = TextStyle(fontSize = 12.sp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            modifier = Modifier.width(300.dp),
            onClick = {
                validateInput()
                if (emailError == null && passwordError == null)
                    registerUser(email, password, auth, navController)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray,
                contentColor = Color.Black,
                disabledContainerColor = Color.DarkGray,
                disabledContentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Sign in")
        }
    }
}

private fun registerUser(
    email: String,
    password: String,
    auth: FirebaseAuth,
    navController: NavController,
) {
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Registration successful
                navController.navigate(NavRoutes.Login.route) {
                    popUpTo(NavRoutes.Register.route) { inclusive = true }
                }
            } else {
                // Registration failed

            }
        }
}



