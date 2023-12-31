package com.icetetik.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.icetetik.R

class NoteEditText: AppCompatEditText {
    private lateinit var mRect: Rect
    private lateinit var mPaint: Paint


    constructor(context: Context) : super(context){
        init()
    }


    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        init()
    }


    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ){
        init()
    }

    private fun init(){
        mRect = Rect()
        mPaint = Paint()
        mPaint.style = Paint.Style.FILL_AND_STROKE
        mPaint.color = resources.getColor(R.color.primaryBackgroundColor)
        mPaint.strokeWidth = 4F
    }

    override fun onDraw(canvas: Canvas?) {
        val height = height
        val lineHeight = lineHeight
        var count = height / lineHeight

        if (lineCount > count){
            count = lineCount
        }

        val r = mRect
        val paint = mPaint
        var baseline =getLineBounds(0, r) //first line

        for (i in 0 until count) {
            canvas?.drawLine(r.left.toFloat(), (baseline + 16).toFloat(), r.right.toFloat(), (baseline + 16).toFloat(), paint)
            baseline += lineHeight
        }

        super.onDraw(canvas)
    }

}