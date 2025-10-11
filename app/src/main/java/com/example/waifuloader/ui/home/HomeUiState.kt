package com.example.waifuloader.ui.home

import com.example.waifuloader.data.models.Waifu

data class HomeUiState(
    val isLoading: Boolean = false,
    val currentWaifu: Waifu = Waifu("", "")
)
