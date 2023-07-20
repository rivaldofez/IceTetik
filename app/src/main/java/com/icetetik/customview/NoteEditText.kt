package com.icetetik.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.github.mikephil.charting.utils.Utils.init
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
        mPaint.setColor(resources.getColor(R.color.primaryBackgroundColor))
        mPaint.strokeWidth = 4F
    }

    override fun onDraw(canvas: Canvas?) {
        val height = height
        val line_height = lineHeight
        var count = height / line_height

        if (lineCount > count){
            count = lineCount
        }

        val r = mRect
        val paint = mPaint
        var baseline =getLineBounds(0, r) //first line

        for (i in 0..count-1) {
            canvas?.drawLine(r.left.toFloat(), (baseline + 16).toFloat(), r.right.toFloat(), (baseline + 16).toFloat(), paint)
            baseline += lineHeight
        }

        super.onDraw(canvas)
    }

}