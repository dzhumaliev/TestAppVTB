package com.io.testappvtb.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.io.testappvtb.data.models.VideoModel
import com.io.testappvtb.data.repo.VideoRepo
import com.io.testappvtb.parseServerError
import kotlinx.coroutines.launch

class FirstViewModel(private val repo: VideoRepo) : ViewModel() {

    private val _state = MutableLiveData<VideoModel>()
    val state: LiveData<VideoModel> = _state

    private val _stateError = MutableLiveData<String>()
    val stateError: LiveData<String> = _stateError

    fun getVideo() {
        viewModelScope.launch {
            val response = repo.getVideo()

            if (response != null) {
                when {
                    response.isSuccessful -> {
                        _state.postValue(response.body())
                    }
                    else -> if (response.errorBody() != null) {
                        _stateError.postValue(parseServerError(response.code()))
                    }
                }
            }
        }
    }
}