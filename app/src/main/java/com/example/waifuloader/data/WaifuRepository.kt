package com.example.waifuloader.data

import com.example.waifuloader.data.models.ImageData
import com.example.waifuloader.data.models.NetworkResult

interface WaifuRepository {
    suspend fun getWaifuInfo(): NetworkResult<ImageData>
}