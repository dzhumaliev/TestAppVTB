package com.io.testappvtb.module

import com.io.testappvtb.data.api.VideoApi
import com.io.testappvtb.data.api.createNetworkClient
import org.koin.dsl.module


private val API: VideoApi = createNetworkClient().create(VideoApi::class.java)

val networkModule = module {
    single { API }
}