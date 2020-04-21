package com.haomins.reader.utils

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar.ANIMATION_MODE_SLIDE
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_SHORT
import com.google.android.material.snackbar.Snackbar
import com.haomins.reader.R

fun Fragment.showSnackbar(string: String) {
    Snackbar.make(this.requireView(), string, LENGTH_SHORT)
        .setBackgroundTint(requireContext().getColor(R.color.default_text))
        .setAnimationMode(ANIMATION_MODE_SLIDE)
        .apply {
            setAction(R.string.dismiss) { this.dismiss() }
        }
        .show()
}