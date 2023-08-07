package com.icetetik.page.infographic

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.icetetik.data.model.Infographic
import com.icetetik.databinding.ActivityDetailInfographicBinding
import com.icetetik.util.Extension.showSnackBar
import com.icetetik.util.KeyParcelable

class DetailInfographicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailInfographicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailInfographicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        val infographic = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(KeyParcelable.INFOGRAPHIC_DATA, Infographic::class.java)
        } else {
            intent.getParcelableExtra(KeyParcelable.INFOGRAPHIC_DATA)
        }

        if (infographic == null){
            binding.showSnackBar("Terjadi kesalahan saat memuat infographic, silakan coba lagi")
        } else {
            Glide.with(this)
                .load(infographic.url)
                .into(binding.ivInfographic)
        }
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
}