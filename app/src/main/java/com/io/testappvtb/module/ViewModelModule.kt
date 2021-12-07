package com.io.testappvtb.module

import com.io.testappvtb.ui.fragments.FirstViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { FirstViewModel(get()) }
}