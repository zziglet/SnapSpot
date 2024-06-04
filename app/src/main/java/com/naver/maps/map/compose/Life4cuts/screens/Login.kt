package com.naver.maps.map.compose.Life4cuts.screens

import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen() {
    val auth = FirebaseAuth.getInstance()
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    if (auth.currentUser != null) {
        // 이미 로그인된 경우
        Text("이미 로그인되었습니다.")
    } else {
        // 로그인되지 않은 경우
        OutlinedTextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("이메일") }
        )
        OutlinedTextField(
            value = password.value,
            onValueChange = { password.value = it },
            label = { Text("비밀번호") }
        )
        Button(onClick = {
            auth.signInWithEmailAndPassword(email.value, password.value)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Text("로그인 성공!")
                    } else {
                        Text("로그인 실패: ${task.exception?.message}")
                    }
                }
        }) {
            Text("로그인")
        }
    }
}
@Composable
fun RegisterScreen() {
    val auth = FirebaseAuth.getInstance()
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }

    OutlinedTextField(
        value = email.value,
        onValueChange = { email.value = it },
        label = { Text("이메일") }
    )
    OutlinedTextField(
        value = password.value,
        onValueChange = { password.value = it },
        label = { Text("비밀번호") }
    )
    Button(onClick = {
        auth.createUserWithEmailAndPassword(email.value, password.value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Text("회원가입 성공!")
                } else {
                    Text("회원가입 실패: ${task.exception?.message}")
                }
            }
    }) {
        Text("회원가입")
    }
}
