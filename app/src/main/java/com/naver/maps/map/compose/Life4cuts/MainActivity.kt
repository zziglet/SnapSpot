package com.naver.maps.map.compose.Life4cuts

import android.os.Bundle
import androidx.activity.ComponentActivity
import com.naver.maps.map.compose.demo.NaverMapTheme
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.google.android.recaptcha.Recaptcha
import com.google.android.recaptcha.RecaptchaClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var recaptchaClient: RecaptchaClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeRecaptchaClient()

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        setContent {
            NaverMapTheme {
                MainScreen(auth,firestore)
            }
        }
    }
    private fun initializeRecaptchaClient() {
        lifecycleScope.launch {
            Recaptcha.getClient(application, "6LdpXfcpAAAAALOIhPXq3Za2HN9l8_eSBfvfh6Lw")
                .onSuccess { client ->
                    recaptchaClient = client
                }
                .onFailure { exception ->
                    // Handle communication errors ...
                    // See "Handle communication errors" section
                }
        }
    }
}