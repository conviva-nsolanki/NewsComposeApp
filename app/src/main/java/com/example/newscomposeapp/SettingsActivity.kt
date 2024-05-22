package com.example.newscomposeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.newscomposeapp.ui.theme.NewsComposeAppTheme

class SettingsActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsComposeAppTheme {
                Scaffold { padding ->
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight().padding(padding)) {
                        Text(text = "This is settings activity")
                    }
                }
            }
        }
    }
}