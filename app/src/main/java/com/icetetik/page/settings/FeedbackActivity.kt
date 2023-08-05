package com.icetetik.page.settings

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.icetetik.R
import com.icetetik.databinding.ActivityFeedbackBinding

class FeedbackActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedbackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonActions()
    }

    private fun setButtonActions() {
        binding.apply {
            btnGform.setOnClickListener {
                val url = "https://docs.google.com/forms/d/e/1FAIpQLSf2l1feA6XKBsUjJENbUknRZpOj01Lz4v8AW8Zl4KNoC9IMOQ/viewform"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }

            btnLinktree.setOnClickListener {
                val url = "https://linktr.ee/icetetik.co"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        }
    }
}