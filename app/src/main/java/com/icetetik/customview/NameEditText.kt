package com.icetetik.customview

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.icetetik.R

class NameEditText: AppCompatEditText {
    constructor(context: Context) : super(context){
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        init()
    }

    private fun init(){
        inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME


        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (!p0.isNullOrEmpty() && p0.length < 2 ){
                    error = context.getString(R.string.txt_minimum_two_character_fullname)
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }
}