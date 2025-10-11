@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.waifuloader.ui.saved

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.waifuloader.data.models.Waifu
import com.example.waifuloader.ui.LocalWaifuStore
import com.example.waifuloader.ui.saved.composables.SavedWaifuCard

@Composable
fun SavedWaifuGridRoute(
    onImageClick: (String) -> Unit = {},
    onBackClick: () -> Unit = {}
) {
    val waifus by LocalWaifuStore.current.waifuList.collectAsState()

    SavedWaifuGridScreen(
        images = waifus,
        onWaifuClick = onImageClick,
        onBackClick = onBackClick
    )
}

@Composable
fun SavedWaifuGridScreen(
    images: List<Waifu>,
    onWaifuClick: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Saved Waifus") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(images) { waifu ->
                    SavedWaifuCard(waifu = waifu, onClick = { onWaifuClick(waifu.id) })
                }
            }
        }
    }
}