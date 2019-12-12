package com.example.composenetworking.ui

import android.util.Log
import androidx.annotation.CheckResult
import androidx.compose.*
import androidx.core.graphics.drawable.toBitmap
import androidx.ui.core.Clip
import androidx.ui.core.dp
import androidx.ui.foundation.DrawImage
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Image
import androidx.ui.layout.Container
import androidx.ui.res.imageResource
import coil.Coil
import coil.api.newGetBuilder
import coil.request.GetRequest
import com.example.composenetworking.R
import com.example.composenetworking.data.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@CheckResult(suggest = "+")
fun image(data: Any) = effectOf<Image?> {
    // Positionally memoize the request creation so
    // it will only be recreated if data changes.

    val request = +memo(data) {
        Coil.loader().newGetBuilder().data(data).build()
    }
    +image(request)
}

/**
 * A configurable [image] effect, which accepts a [request] value object.
 */
@CheckResult(suggest = "+")
fun image(request: GetRequest) = effectOf<Image?> {
    val image = +state<Image?> { null }
    // Execute the following code whenever the request changes.
    +onCommit(request) {
        val job = CoroutineScope(Dispatchers.IO).launch {
            // Start loading the image and await the result.
            val drawable = Coil.loader().get(request)
            withContext(Dispatchers.Main){
                image.value = AndroidImage(drawable.toBitmap())
            }
        }
        // Cancel the request if the input to onCommit changes or
        // the Composition is removed from the composition tree.
        onDispose { job.cancel() }
    }

    // Emit a null Image to start with.
    image.value
}

@Composable
fun PostImage(article: Article) {
    if (article.url.isNullOrEmpty()) {

    } else {
        val image = +image(article.urlToImage) ?: +imageResource(R.drawable.place_holder)
        Container(expanded = true, height = 300.dp) {
            Clip(shape = RoundedCornerShape(4.dp)) {
                DrawImage(image)
            }
        }
    }

}