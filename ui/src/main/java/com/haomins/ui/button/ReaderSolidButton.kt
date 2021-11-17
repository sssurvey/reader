package com.haomins.ui.button

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import com.haomins.ui.R

class ReaderSolidButton : AppCompatButton {

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun setClickable(clickable: Boolean) {
        super.setClickable(clickable)
        if (clickable) {
            background = AppCompatResources.getDrawable(context, R.drawable.reader_solid_button_background)!!
        } else {
            background = AppCompatResources.getDrawable(context, R.drawable.reader_solid_button_background)!!.apply {
                alpha = OPAQUE_LEVEL
            }
        }
    }

    companion object {
        private const val OPAQUE_LEVEL: Int = 255 / 2
    }

}