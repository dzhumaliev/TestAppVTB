package com.io.testappvtb.data.repo

import com.io.testappvtb.data.api.VideoApi
import com.io.testappvtb.data.models.VideoModel
import retrofit2.Response

class VideoRepo(private val api: VideoApi) {

    suspend fun getVideo(): Response<VideoModel>? = try {
        api.getVideo()
    } catch (e: Exception) {
        null
    }

}