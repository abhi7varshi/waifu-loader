package com.example.waifuloader.data.repository

import com.example.waifuloader.data.WaifuRepository
import com.example.waifuloader.data.models.ImageData
import com.example.waifuloader.data.models.NetworkResult
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

private interface NetworkApi {
    @GET("/search")
    suspend fun getWaifu(
        @Query("included_tags") tag: String
    ): Response<ResponseBody>
}

class RetrofitWaifuRepository @Inject constructor(
    private val networkJson: Json,
    private val okHttpCallFactory: Call.Factory
) : WaifuRepository {
    private val baseURL = "https://api.waifu.im"

    private val networkApi = Retrofit.Builder()
        .baseUrl(baseURL)
        .callFactory { okHttpCallFactory.newCall(it) }
        .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(NetworkApi::class.java)

    override suspend fun getWaifuInfo(): NetworkResult<ImageData> {
        return try {
            val response = networkApi.getWaifu(tag = "maid")

            if (response.isSuccessful) {
                val body = response.body()?.string()
                if (body.isNullOrEmpty()) {
                    return NetworkResult.Error(
                        code = response.code(),
                        message = "Empty response body"
                    )
                }

                // Parse JSON manually with kotlinx.serialization.Json
                val jsonElement = networkJson.parseToJsonElement(body)
                val imagesArray = jsonElement.jsonObject["images"]?.jsonArray

                if (imagesArray.isNullOrEmpty()) {
                    return NetworkResult.Error(
                        code = response.code(),
                        message = "No images found"
                    )
                }

                // Take the first image
                val imageObj = imagesArray.first().jsonObject
                val imageId = imageObj["image_id"]?.toString()?.trim('"') ?: ""
                val url = imageObj["url"]?.toString()?.trim('"') ?: ""

                val imageData = ImageData(
                    imageId = imageId,
                    url = url
                )

                NetworkResult.Success(imageData)
            } else {
                // HTTP-level error
                NetworkResult.Error(
                    code = response.code(),
                    message = response.errorBody()?.string() ?: "Unknown error"
                )
            }

        } catch (e: Exception) {
            // Network or parsing exception
            NetworkResult.Error(
                code = -1,
                message = e.message ?: "Unexpected error"
            )
        }
    }
}