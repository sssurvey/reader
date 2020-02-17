package com.haomins.reader.utils

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment


private const val NULL = "NULL - NULL - NULL"

fun Activity.showToast(message: String?) {
    if (message == null) {
        Toast.makeText(this, NULL, Toast.LENGTH_LONG).show()
    } else {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}

fun Fragment.showToast(message: String?) {
    if (message == null) {
        Toast.makeText(this.context, NULL, Toast.LENGTH_LONG).show()
    } else {
        Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
    }
}
