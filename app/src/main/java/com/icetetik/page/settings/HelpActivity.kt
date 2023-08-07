package com.icetetik.page.settings

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.icetetik.R
import com.icetetik.databinding.ActivityHelpBinding

class HelpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtonActions()
    }

    private fun setButtonActions() {
        binding.apply {
            btnIg.setOnClickListener {

                val url = "https://www.instagram.com/icetetik.co/"
                val intent = Intent(Intent.ACTION_VIEW)

                intent.data = Uri.parse(url)
                startActivity(intent)
            }

            btnContactPerson.setOnClickListener {
                val url = "https://wa.me/message/SOLC3GUTD6OVF1?src=qr"
                val intent = Intent(Intent.ACTION_VIEW)

                intent.data = Uri.parse(url)
                startActivity(intent)
            }
        }
    }
}