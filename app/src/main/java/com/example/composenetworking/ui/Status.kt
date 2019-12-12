package com.example.composenetworking.ui

import androidx.compose.Model
import com.example.composenetworking.data.Article

sealed class Screen {
    object Home : Screen()
//    data class ArticleDetail(article: Article) : Sc
    data class Detail(val article: Article) : Screen()
    //data class ScreenWithParameters(val id: Int) : Screen()
}

@Model
object MyAppStatus {
    var currentScreen: Screen = Screen.Home
}

/**
 * Temporary solution pending navigation support.
 */
fun navigateTo(destination: Screen) {
    MyAppStatus.currentScreen = destination
}