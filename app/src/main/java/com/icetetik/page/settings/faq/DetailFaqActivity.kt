package com.icetetik.page.settings.faq

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.icetetik.R
import com.icetetik.databinding.ActivityDetailFaqBinding
import com.icetetik.util.Extension.showSnackBar
import com.icetetik.util.KeyParcelable

class DetailFaqActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailFaqBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailFaqBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val title = intent.getStringExtra(KeyParcelable.TITLE_FAQ)
        val answer = intent.getStringExtra(KeyParcelable.ANSWER_FAQ)

        if( title.isNullOrEmpty() || answer.isNullOrEmpty() ){
            binding.showSnackBar(getString(R.string.error_faq_null))
        } else {
            binding.apply {
                tvTitleFaq.text = title
                tvAnswerFaq.text = answer
            }
        }
    }
}