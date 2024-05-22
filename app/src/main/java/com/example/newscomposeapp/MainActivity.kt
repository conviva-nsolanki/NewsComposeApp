package com.example.newscomposeapp

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.conviva.apptracker.controller.TrackerController
import com.example.newscomposeapp.ui.NewsComposeApp
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var tracker: TrackerController
    private val viewModel: MainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NewsComposeApp(viewModel, tracker)
        }
        viewModel.openSecondActivity.observe(this) { open ->
            if (open) {
                try {
                    val intent: Intent = Intent(
                        this@MainActivity,
                        SettingsActivity::class.java
                    )
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.localizedMessage?.let { it1 -> Log.d("MainActivity", it1) }
                }
            }
        }
    }
}