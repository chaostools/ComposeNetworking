package com.example.composenetworking.ui.home

import androidx.compose.*
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Text
import androidx.ui.core.dp
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.VerticalScroller
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.layout.*
import androidx.ui.material.*
import androidx.ui.material.ripple.Ripple
import androidx.ui.material.surface.Card
import androidx.ui.res.imageResource

import com.example.composenetworking.R
import com.example.composenetworking.data.Article
import com.example.composenetworking.repo.PaperNewRepository
import com.example.composenetworking.ui.PostImage
import com.example.composenetworking.ui.Screen
import com.example.composenetworking.ui.navigateTo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Model
class ScheduleModel(
    var isLoading: Boolean = true,
    var listDataPaper: MutableList<Article> = mutableListOf()
) {
    fun fetchData() {
        isLoading = true
        CoroutineScope(Dispatchers.IO).launch {
            val dataPaper = PaperNewRepository().getNewPaper()
            withContext(Dispatchers.Main) {
                listDataPaper.clear()
                if (dataPaper != null) {
                    listDataPaper.addAll(dataPaper.articles)
                }
                isLoading = false
            }
        }
    }
}

@Composable
fun HomeScreen() {
    val model = +memo { ScheduleModel() }

    val refreshImage = +imageResource(R.drawable.ic_refresh)

    +onActive {
        model.fetchData()
    }

    FlexColumn {
        inflexible {
            TopAppBar(
                title = { Text("New paper", style = (+themeTextStyle { h6 })) },
                color = +themeColor { background },
                actionData = listOf(refreshImage)
            ) { actionImage ->
                AppBarIcon(
                    icon = actionImage,
                    onClick = { model.fetchData() })
            }
        }
        flexible(flex = 1f) {
            if (model.isLoading) {
                Column(
                    crossAxisSize = LayoutSize.Expand,
                    crossAxisAlignment = CrossAxisAlignment.Center,
                    modifier = Spacing(16.dp)
                ) {
                    CircularProgressIndicator(color = +themeColor { secondaryVariant })
                }
            } else {
                val paperItems = model.listDataPaper
                if (paperItems.isNotEmpty()) {
                    PaperColumn(paperListItem = paperItems)
                }
            }
        }
    }
}

@Composable
fun PaperColumn(paperListItem: List<Article>) {
    VerticalScroller {
        Column(
            crossAxisSize = LayoutSize.Expand,
            crossAxisAlignment = CrossAxisAlignment.Stretch,
            modifier = Spacing(16.dp)
        ) {
            for (item in paperListItem) {
                SchedulerItem(item)
                HeightSpacer(height = 16.dp)
            }
        }
    }
}

@Composable
fun SchedulerItem(item: Article) {

    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        color = +themeColor { surface }
    ) {
        Ripple(bounded = true) {
            Clickable(
                onClick = {
                    navigateTo(Screen.Detail(item))
                }
            ) {
                Padding(padding = 12.dp) {
                    Column(
                        mainAxisAlignment = MainAxisAlignment.Center,
                        mainAxisSize = LayoutSize.Expand,
                        crossAxisSize = LayoutSize.Expand
                    ) {
                        PostImage(article = item)

                        if (item.author.isNullOrEmpty()) {
                            Text(text = "Noname",
                                style = +themeTextStyle { h6 }
                            )
                        } else {
                            Text(text = item.author,
                                style = +themeTextStyle { h6 }
                            )
                        }
                        HeightSpacer(height = 16.dp)

                        if (item.content.isNullOrEmpty()) {
                            Text(text = "",
                                style = +themeTextStyle { body2 })
                        } else {
                            Text(text = item.content,
                                style = +themeTextStyle { body2 })
                        }

                        HeightSpacer(height = 8.dp)

                        if (item.description.isNullOrEmpty()) {
                            Text(text = "",
                                style = +themeTextStyle { overline })
                        } else {
                            Text(text = item.description,
                                style = +themeTextStyle { overline })
                        }
                        HeightSpacer(height = 8.dp)

                    }
                }
            }
        }
    }
}