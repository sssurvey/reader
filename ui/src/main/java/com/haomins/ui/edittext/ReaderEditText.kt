package com.haomins.ui.edittext

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.haomins.ui.R

open class ReaderEditText : AppCompatEditText {

    init {
        background = context.getDrawable(R.drawable.reader_edit_text_background)
        this.setPadding(HORIZONTAL_PADDING, VERTICAL_PADDING, HORIZONTAL_PADDING, VERTICAL_PADDING)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    companion object {
        private const val HORIZONTAL_PADDING = 60
        private const val VERTICAL_PADDING = 0
    }
}
