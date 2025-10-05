package com.example.waifuloader.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ImageData(
    @SerialName("image_id") val imageId: String = "",
    val url: String = ""
)
