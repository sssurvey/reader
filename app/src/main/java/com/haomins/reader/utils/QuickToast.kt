package com.haomins.reader.utils

import android.content.Context
import android.widget.Toast

object QuickToast {

    private const val NULL = "NULL - NULL - NULL"

    fun show(applicationContext: Context, message: String?) {
        if (message == null) {
            Toast.makeText(applicationContext, NULL, Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
        }
    }

}
