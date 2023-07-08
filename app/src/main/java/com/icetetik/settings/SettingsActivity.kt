package com.icetetik.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.icetetik.R
import com.icetetik.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_settings)
        DataBindingUtil.setContentView<ActivitySettingsBinding>(this,  R.layout.activity_settings)

    }
}