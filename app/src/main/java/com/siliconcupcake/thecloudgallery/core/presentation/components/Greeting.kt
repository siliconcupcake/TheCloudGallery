package com.siliconcupcake.thecloudgallery.core.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.tv.material3.Text
import com.siliconcupcake.thecloudgallery.ui.theme.TheCloudGalleryTheme

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TheCloudGalleryTheme {
        Greeting("Android")
    }
}