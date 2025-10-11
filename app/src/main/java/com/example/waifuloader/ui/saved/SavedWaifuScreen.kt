@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.waifuloader.ui.saved

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.waifuloader.data.models.Waifu
import com.example.waifuloader.ui.LocalWaifuStore

@Composable
fun SavedWaifuRoute(
    id: String,
    onBackClick: () -> Unit,
) {
    val waifuStore = LocalWaifuStore.current

    SavedWaifuScreen(
        waifu = waifuStore.getWaifuById(id),
        onBackClick = onBackClick,
        onDeleteClick = { id -> waifuStore.removeWaifu(id) }
    )
}

@Composable
fun SavedWaifuScreen(
    waifu: Waifu?,
    onBackClick: () -> Unit,
    onDeleteClick: (String) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Saved Waifu") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { showDialog = true }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            val painter = rememberAsyncImagePainter(model = waifu?.url)

            if (painter.state is AsyncImagePainter.State.Loading) {
                CircularProgressIndicator()
            }

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text("Confirm") },
                text = { Text("Are you sure you want to delete your cutie waifu? 💔") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            showDialog = false
                            waifu?.id?.let { onDeleteClick(it) }
                            onBackClick()
                        }
                    ) {
                        Text("Yes, delete")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }
    }
}
