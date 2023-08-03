package com.icetetik.page.video

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.icetetik.R
import com.icetetik.data.model.Video
import com.icetetik.databinding.ActivityVideoBinding
import com.icetetik.util.Extension.animateChangeVisibility
import com.icetetik.util.Extension.showSnackBar
import com.icetetik.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoActivity : AppCompatActivity(), VideoItemCallback {
    private lateinit var binding: ActivityVideoBinding
    private val viewModel: VideoViewModel by viewModels()
    private val adapter = VideoAdapter(this@VideoActivity, this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObservers()
        setupRecycler()
        viewModel.getVideos()

    }

    private fun setupRecycler() {
        val layoutManager = LinearLayoutManager(this@VideoActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.rvVideo.layoutManager = layoutManager
        binding.rvVideo.adapter = adapter
    }

    private fun setObservers() {
        viewModel.videos.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    showLoading(isLoading = true)
                }

                is UiState.Failure -> {
                    showLoading(isLoading = false)
                    binding.showSnackBar(getString(R.string.error_process_request))
                }

                is UiState.Success -> {
                    showLoading(isLoading = false)
                    updateRecyclerData(state.data)
                }
            }
        }
    }

    private fun updateRecyclerData(videos: List<Video>) {
        adapter.setData(videos)
    }

    override fun onItemVideoClick(video: Video) {

    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            sblLoading.root.animateChangeVisibility(isLoading)
            rvVideo.animateChangeVisibility(!isLoading)

            if (isLoading) sblLoading.lottieLoading.playAnimation() else sblLoading.lottieLoading.pauseAnimation()
        }
    }
}