package com.io.testappvtb.module


import com.io.testappvtb.data.repo.VideoRepo
import org.koin.dsl.module

val repositoryModule = module {
    single { VideoRepo(get()) }

}