package com.icetetik.page.infographic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.icetetik.R
import com.icetetik.data.model.Infographic
import com.icetetik.databinding.ActivityInfographicBinding
import com.icetetik.util.Extension.animateChangeVisibility
import com.icetetik.util.Extension.showSnackBar
import com.icetetik.util.UiState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfographicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfographicBinding
    private val viewModel: InfographicViewModel by viewModels()
    private val adapter = InfographicAdapter(this@InfographicActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInfographicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setObservers()
        viewModel.getInfographics()

        setupViewPager()
        setupToolbar()
        setupButtonActions()


    }

//    private fun setupButtonActions() {
//        binding.apply {
//            btnNextImage.setOnClickListener {
//                val currentItem = binding.vpImages.currentItem
//                if(currentItem != adapter.itemCount - 1){
//                    vpImages.setCurrentItem(currentItem + 1, true)
//                }
//            }
//
//            btnPrevImage.setOnClickListener {
//                val currentItem = binding.vpImages.currentItem
//                if(currentItem != 0){
//                    vpImages.setCurrentItem(currentItem - 1, true)
//                }
//            }
//        }
//    }

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
                    updateViewPagerData(state.data)
                    binding.indicatorImages.setViewPager(binding.vpImages)
                }
            }
        }
    }

    private fun updateViewPagerData(infographics: List<Infographic>) {
        adapter.setData(infographics)
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setupViewPager() {
        binding.apply {
            vpImages.adapter = adapter
            vpImages.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            vpImages.isUserInputEnabled = false

            vpImages.currentItem = 1
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.apply {
            sblLoading.root.animateChangeVisibility(isLoading)
            vpImages.animateChangeVisibility(!isLoading)

            if (isLoading) sblLoading.lottieLoading.playAnimation() else sblLoading.lottieLoading.pauseAnimation()
        }
    }
}