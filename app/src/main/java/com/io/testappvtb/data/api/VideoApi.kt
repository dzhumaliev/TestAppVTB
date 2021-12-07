package com.io.testappvtb.data.api

import com.io.testappvtb.data.models.VideoModel
import retrofit2.Response
import retrofit2.http.GET

interface VideoApi {

    @GET("test/item")
    suspend fun getVideo(): Response<VideoModel>
}