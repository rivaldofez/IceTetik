package com.icetetik.page.video

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.icetetik.data.model.Video
import com.icetetik.data.repository.VideoRepository
import com.icetetik.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoViewModel @Inject constructor(
    private val videoRepository: VideoRepository
): ViewModel() {

    private val _videos = MutableLiveData<UiState<List<Video>>>()
    val videos: LiveData<UiState<List<Video>>>
        get() = _videos

    private val _addVideos = MutableLiveData<UiState<String>>()
    val addVideos: LiveData<UiState<String>>
        get() = _addVideos


    fun getVideos(){
        _videos.value = UiState.Loading
        videoRepository.getVideos { _videos.value = it }
    }

    fun addVideos(){
        _addVideos.value = UiState.Loading
        videoRepository.setVideos { _addVideos.value = it }
    }


}