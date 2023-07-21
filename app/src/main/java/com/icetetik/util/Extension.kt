package com.icetetik.util

import android.animation.ObjectAnimator
import android.view.View
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

object Extension {
    fun View.animateChangeVisibility(isVisible: Boolean, duration: Long = 300) =
        ObjectAnimator
            .ofFloat(this, View.ALPHA, if (isVisible) 1f else 0f)
            .setDuration(duration)
            .start()

    fun ViewBinding.showSnackBar(message: String) {
        Snackbar.make(
            this.root,
            message,
            Snackbar.LENGTH_SHORT
        ).show()
    }
}