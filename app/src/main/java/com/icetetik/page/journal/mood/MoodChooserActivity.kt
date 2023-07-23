package com.icetetik.page.journal.mood

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.icetetik.R
import com.icetetik.data.model.MoodCondition
import com.icetetik.data.model.MoodItemView
import com.icetetik.databinding.ActivityMoodChooserBinding
import com.icetetik.util.KeyParcelable
import com.icetetik.util.MoodConstants

class MoodChooserActivity : AppCompatActivity(), MoodItemCallback {
    private lateinit var binding: ActivityMoodChooserBinding
    private val adapter = MoodAdapter(this@MoodChooserActivity, this)

    private val listMoodItemView = MoodConstants.moodItemViews

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
        intent.putExtra(KeyParcelable.MOOD_CONDITION, moodItemView)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}