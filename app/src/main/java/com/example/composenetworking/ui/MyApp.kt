package com.example.composenetworking.ui

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.animation.Crossfade
import androidx.ui.material.MaterialTheme
import androidx.ui.material.surface.Surface
import androidx.ui.material.themeColor
import com.example.composenetworking.ui.detail.DetailScreen
import com.example.composenetworking.ui.home.HomeScreen

@Composable
fun MyApp(nightMode: Boolean) {
    MaterialTheme(
        colors = if (nightMode) darkThemeColors else lightThemeColors,
        typography = themeTypography
    ) {
        AppContent()
    }
}

@Composable
private fun AppContent() {
    Crossfade(MyAppStatus.currentScreen) { screen ->
        Surface(color = +themeColor { background }) {
            when (screen) {
                is Screen.Home -> HomeScreen()
                is Screen.Detail -> DetailScreen(article = screen.article)
            }
        }
    }
}