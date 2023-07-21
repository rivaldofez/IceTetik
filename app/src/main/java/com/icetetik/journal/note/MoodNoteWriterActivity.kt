package com.icetetik.journal.note

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.icetetik.databinding.ActivityMoodNoteWriterBinding
import com.icetetik.util.KeyParcelable

class MoodNoteWriterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMoodNoteWriterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMoodNoteWriterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()

        binding.btnSave.setOnClickListener {
            val note = binding.edtNote.text
            if (note.isNullOrEmpty()){

            } else {
                val intent = Intent()
                intent.putExtra(KeyParcelable.MOOD_NOTE, note.toString())
                setResult(Activity.RESULT_OK, intent)
                finish()
            }


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