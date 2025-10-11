package com.example.waifuloader.ui.saved

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.waifuloader.data.models.ImageData
import com.example.waifuloader.ui.saved.composables.SavedWaifuCard

@Composable
fun SavedRoute(
    onImageClick: (ImageData) -> Unit = {}
) {
    // 🔹 Dummy list of saved images
    val dummyList = listOf(
        ImageData(imageId = "1", url = "https://cdn.waifu.im/7998.png"),
        ImageData(imageId = "2", url = "https://cdn.waifu.im/1859.jpg"),
        ImageData(imageId = "3", url = "https://cdn.waifu.im/7246.png"),
        ImageData(imageId = "4", url = "https://cdn.waifu.im/8108.png"),
        ImageData(imageId = "5", url = "https://cdn.waifu.im/2802.png"),
        ImageData(imageId = "6", url = "https://cdn.waifu.im/916.jpeg"),
        ImageData(imageId = "7", url = "https://cdn.waifu.im/7852.jpg"),
        ImageData(imageId = "8", url = "https://cdn.waifu.im/7445.jpg"),
        ImageData(imageId = "9", url = "https://cdn.waifu.im/6653.jpeg"),
        ImageData(imageId = "10", url = "https://cdn.waifu.im/3361.jpg"),
    )

    SavedScreen(
        images = dummyList,
        onImageClick = onImageClick
    )
}

@Composable
fun SavedScreen(
    images: List<ImageData>,
    onImageClick: (ImageData) -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.background,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            // 🧩 Grid of images
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(images) { image ->
                    SavedWaifuCard(
                        imageData = image,
                        onClick = { onImageClick(image) }
                    )
                }
            }
        }
    }
}