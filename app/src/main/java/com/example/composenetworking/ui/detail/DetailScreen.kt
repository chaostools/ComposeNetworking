package com.example.composenetworking.ui.detail

import androidx.compose.Composable
import androidx.compose.unaryPlus
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.tooling.preview.Preview
import com.example.composenetworking.R
import com.example.composenetworking.data.Article
import com.example.composenetworking.ui.Screen
import com.example.composenetworking.ui.VectorImageButton
import com.example.composenetworking.ui.home.PaperColumn
import com.example.composenetworking.ui.navigateTo

@Composable
fun DetailScreen(article: Article) {
    FlexColumn {
        inflexible {
            TopAppBar(
                title = {
                    if (article.author!!.length < 20) {
                        Text(
                            text = article.author,
                            style = +themeTextStyle { h6 }
                        )
                    } else {
                        Text(
                            text = article.author.substring(0, 20) + " ...",
                            style = +themeTextStyle { h6 }
                        )
                    }
                },
                navigationIcon = {
                    VectorImageButton(R.drawable.ic_baseline_arrow_back_24) {
                        navigateTo(Screen.Home)
                    }
                }
            )
        }
        flexible(flex = 1f) {
        }
    }
}