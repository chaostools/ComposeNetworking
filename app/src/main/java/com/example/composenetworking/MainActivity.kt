package com.example.composenetworking

import android.content.res.Configuration
import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.Composable
import androidx.ui.core.Text
import androidx.ui.core.setContent
import androidx.ui.material.MaterialTheme
import androidx.ui.tooling.preview.Preview
import com.example.composenetworking.ui.MyApp

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val currentNightMode =
            applicationContext.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK

        setContent {
            MyApp(currentNightMode == Configuration.UI_MODE_NIGHT_YES)
        }


    }
}

