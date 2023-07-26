package com.icetetik.relaxation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.chip.Chip
import com.icetetik.R
import com.icetetik.databinding.ActivityEmotionBinding

class EmotionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmotionBinding

    private val positiveEmotion = ArrayList<Chip>()
    private val titleButton = listOf(
        "Test1", "Test2", "Test3", "Test4", "Test5", "Test6", "Test7"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmotionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        titleButton.forEach {
            val chip = Chip(this@EmotionActivity)
            chip.text = it
            positiveEmotion.add(chip)
        }

        positiveEmotion.forEach {
            binding.cgPositiveEmotion.addView(it)
        }

    }
}