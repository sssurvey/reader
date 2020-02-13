package com.haomins.reader.utils

import android.app.Activity
import android.widget.Toast


private const val NULL = "NULL - NULL - NULL"

fun Activity.showToast(message: String?) {
    if (message == null) {
        Toast.makeText(this, NULL, Toast.LENGTH_LONG).show()
    } else {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}
