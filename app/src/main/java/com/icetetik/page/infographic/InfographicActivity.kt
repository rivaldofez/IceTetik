package com.icetetik.page.infographic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.storage.FirebaseStorage
import com.icetetik.R
import com.icetetik.databinding.ActivityInfographicBinding

class InfographicActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfographicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInfographicBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}