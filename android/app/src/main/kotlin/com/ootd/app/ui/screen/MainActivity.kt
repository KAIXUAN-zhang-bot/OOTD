package com.ootd.app.ui.screen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ootd.app.ui.theme.OOTDTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OOTDTheme {
                OOTDApp()
            }
        }
    }
}

@Composable
fun OOTDApp() {
    Surface(modifier = Modifier.fillMaxSize()) {
        MainScreen()
    }
}

@Composable
fun MainScreen() {
    // Navigation will be implemented here
}
