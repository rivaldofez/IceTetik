package com.icetetik.page.infographic

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.icetetik.R
import com.icetetik.data.model.Infographic
import com.icetetik.databinding.ActivityInfographicBinding
import com.icetetik.page.video.VideoPlayerActivity
import com.icetetik.util.Extension.animateChangeVisibility
import com.icetetik.util.Extension.showSnackBar
import com.icetetik.util.KeyParcelable
import com.icetetik.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfographicActivity : AppCompatActivity(), InfographicItemCallback {
    private lateinit var binding: ActivityInfographicBinding
    private val viewModel: InfographicViewModel by viewModels()
    private val adapter = InfographicAdapter(this@InfographicActivity, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInfographicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObservers()
        setupRecycler()
        viewModel.getInfographics()
    }

    private fun setupRecycler() {
        val layoutManager = LinearLayoutManager(this@InfographicActivity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.rvInfographic.layoutManager = layoutManager
        binding.rvInfographic.adapter = adapter
    }

    private fun setObservers() {
        viewModel.infographics.observe(this) { state ->
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

    private fun updateRecyclerData(infographics: List<Infographic>) {
        adapter.setData(infographics)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            sblLoading.root.animateChangeVisibility(isLoading)
            rvInfographic.animateChangeVisibility(!isLoading)

            if (isLoading) sblLoading.lottieLoading.playAnimation() else sblLoading.lottieLoading.pauseAnimation()
        }
    }

    override fun onItemInfographicClick(infographic: Infographic) {
        val intent = Intent(this@InfographicActivity, DetailInfographicActivity::class.java)
        intent.putExtra(KeyParcelable.INFOGRAPHIC_DATA, infographic)
        startActivity(intent)
    }
}