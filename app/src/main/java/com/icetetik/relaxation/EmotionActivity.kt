package com.icetetik.relaxation

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.setPadding
import com.google.android.material.chip.Chip
import com.icetetik.R
import com.icetetik.databinding.ActivityEmotionBinding

class EmotionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEmotionBinding

    private val positiveEmotionTitleList = listOf(
        "Antusias", "Gembira", "Takjub", "Semangat", "Bangga", "Penuh Cinta", "Santai", "Tenang", "Puas", "Lega", "Senang"
    )

    private val negativeEmotionTitleList = listOf(
        "Marah","Takut", "Stres", "Waspada", "Kewalahan", "Patah Hati", "Bingung", "Kesal", "Malu", "Cemas", "Lesu", "Sedih", "Duka", "Bosan", "Apatis", "Kesepian", "Gelisah", "Murung", "Kecewa", "Hiperaktif"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmotionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        positiveEmotionTitleList.forEach {
            binding.cgPositiveEmotion.addView(generateChipItem(it, this@EmotionActivity))
        }

        negativeEmotionTitleList.forEach {
            binding.cgNegativeEmotion.addView(generateChipItem(it, this@EmotionActivity))
        }

        for(i in 0 until binding.cgPositiveEmotion.childCount){
            val chip = binding.cgPositiveEmotion.getChildAt(i) as Chip
            chip.setOnClickListener {
                if(chip.isCheckable){
                    chip.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(this@EmotionActivity, R.color.white))
                    Log.d("Teston", "called in white")
                } else {
                    chip.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(this@EmotionActivity, R.color.cream_100))
                    Log.d("Teston", "called in cream")
                }
                Log.d("Teston", "called in loop cg emotion")
                chip.isCheckable = !chip.isCheckable
            }
        }

        for(i in 0 until binding.cgNegativeEmotion.childCount){
            val chip = binding.cgNegativeEmotion.getChildAt(i) as Chip
            chip.setOnClickListener {
                if(chip.isCheckable){
                    chip.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(this@EmotionActivity, R.color.white))
                    Log.d("Teston", "called in white")
                } else {
                    chip.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(this@EmotionActivity, R.color.cream_100))
                    Log.d("Teston", "called in cream")
                }
                Log.d("Teston", "called in loop cg emotion")
                chip.isCheckable = !chip.isCheckable
            }
        }

        binding.cgPositiveEmotion.setOnCheckedChangeListener { group, checkedId ->
            Log.d("Teston", "called")
        }
    }


    private fun generateChipItem(text: String, context: Context): Chip {
        val paddingDp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, 8f,
            resources.displayMetrics
        ).toInt()

        val chip = Chip(context)
        chip.id = ViewCompat.generateViewId()
        chip.text = text
        chip.setEnsureMinTouchTargetSize(false)
        chip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
        chip.setTextColor(getColor(R.color.primaryBackgroundColor))
        chip.setPadding(paddingDp)
        chip.minHeight = 30
        chip.isAllCaps = true
        return chip


    }
}