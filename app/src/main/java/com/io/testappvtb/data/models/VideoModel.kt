package com.io.testappvtb.data.models

data class VideoModel(
    var a: String? = null,
    var task_id: Int,
    var status: Int? = null,
    var results: VideoResultModel? = null
)


data class VideoResultModel(
    var single: String? = null,
    var split_v: String? = null,
    var split_h: String? = null,
    var src: String? = null,
    var preview_image: String? = null
)
