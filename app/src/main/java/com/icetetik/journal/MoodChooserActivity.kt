package com.icetetik.journal

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.icetetik.R
import com.icetetik.data.model.MoodItemView
import com.icetetik.databinding.ActivityMoodChooserBinding

class MoodChooserActivity : AppCompatActivity(), MoodItemCallback {
    private lateinit var binding: ActivityMoodChooserBinding
    private val adapter = MoodAdapter(this@MoodChooserActivity, this)


    private val listMoodItemView = listOf(
        MoodItemView(condition = "Happy", image = R.drawable.img_happy),
        MoodItemView(condition = "Sad", image = R.drawable.img_sad),
        MoodItemView(condition = "Angry", image = R.drawable.img_angry),
        MoodItemView(condition = "Shock", image = R.drawable.img_shock),
        MoodItemView(condition = "Scared", image = R.drawable.img_scared),
        MoodItemView(condition = "Disgusting", image = R.drawable.img_disgusting)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoodChooserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = GridLayoutManager(this@MoodChooserActivity, 3)
        binding.rvMood.adapter = adapter
        binding.rvMood.layoutManager = layoutManager
        adapter.setData(listMoodItemView)

        setupToolbar()
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

    override fun onItemMoodCallback(moodItemView: MoodItemView) {
        val intent = Intent()
        intent.putExtra("test", moodItemView)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}