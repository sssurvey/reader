package com.haomins.ui.navigationbar

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.haomins.ui.R

class ReaderNavigationBar : ConstraintLayout {

    init {
        init()
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private fun init() {
        inflate(context, R.layout.reader_navigation_bar, this)
    }

}