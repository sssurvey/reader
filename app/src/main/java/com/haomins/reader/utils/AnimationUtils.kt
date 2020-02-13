package com.haomins.reader.utils

import android.app.Activity
import com.haomins.reader.R

fun Activity.slideInAnimation() {
    this.overridePendingTransition(R.anim.start_slide_in_left, R.anim.start_slide_out_left)
}

fun Activity.slideOutAnimation() {
    this.overridePendingTransition(R.anim.start_slide_in_right, R.anim.start_slide_out_right)
}