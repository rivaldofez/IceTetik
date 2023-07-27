package com.icetetik.relaxation

import android.content.res.ColorStateList
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.core.view.get
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable
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
            chip.id = ViewCompat.generateViewId()
            chip.text = it
            chip.setEnsureMinTouchTargetSize(false)
            chip.isCheckable = true
//            chip.setOnCheckedChangeListener { buttonView, isChecked ->
//                Log.d("Teston", "checked on ${chip.text.toString()}")
//
//            }

            positiveEmotion.add(chip)
        }

        positiveEmotion.forEach {
            binding.cgPositiveEmotion.addView(it)
        }


        for(i in 0 until binding.cgPositiveEmotion.childCount){
            val chip = binding.cgPositiveEmotion.getChildAt(i) as Chip
            chip.setOnClickListener {
                chip.isCheckable = !chip.isCheckable
                if(chip.isCheckable){
                    chip.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(this@EmotionActivity, R.color.white))
                    Log.d("Teston", "called in white")
                } else {
                    chip.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(this@EmotionActivity, R.color.cream_100))
                    Log.d("Teston", "called in cream")
                }
                Log.d("Teston", "called in loop cg emotion")
            }
        }

        binding.cgPositiveEmotion.setOnCheckedChangeListener { group, checkedId ->
            Log.d("Teston", "called")
        }
    }
}