package com.icetetik.util

import android.animation.ObjectAnimator
import android.view.View

object Extension {
    fun View.animateChangeVisibility(isVisible: Boolean, duration: Long = 300) =
        ObjectAnimator
            .ofFloat(this, View.ALPHA, if (isVisible) 1f else 0f)
            .setDuration(duration)
            .start()
}