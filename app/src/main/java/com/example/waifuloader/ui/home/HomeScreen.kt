@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.waifuloader.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.waifuloader.R
import com.example.waifuloader.data.models.Waifu
import com.example.waifuloader.ui.LocalWaifuStore

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateToSaved: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val waifuStore = LocalWaifuStore.current

    HomeScreen(
        uiState = uiState,
        onGetWaifu = viewModel::getWaifu,
        onNavigateToSaved = onNavigateToSaved,
        onSaveWaifu = { waifu -> waifuStore.saveWaifu(waifu) }
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onGetWaifu: () -> Unit,
    onNavigateToSaved: () -> Unit,
    onSaveWaifu: (Waifu) -> Unit,
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    val imageLoader = rememberAsyncImagePainter(
        model = uiState.currentWaifu.url,
        onState = { state ->
            isLoading = state is AsyncImagePainter.State.Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )

    LaunchedEffect(Unit) { onGetWaifu() }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Waifu Loader") },
                actions = {
                    IconButton(onClick = onNavigateToSaved) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Saved Waifus"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Image(
                painter = if (!isError) imageLoader else painterResource(R.drawable.ic_launcher_foreground),
                contentDescription = "Loaded image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(24.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                FloatingActionButton(onClick = { onSaveWaifu(uiState.currentWaifu) }) {
                    Icon(Icons.Outlined.FavoriteBorder, contentDescription = "Save image")
                }

                FloatingActionButton(onClick = { onGetWaifu() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Next image")
                }
            }
        }
    }
}