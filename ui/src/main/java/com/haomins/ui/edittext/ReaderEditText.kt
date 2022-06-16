package com.haomins.ui.edittext

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
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

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        configUiForDarkMode()
    }

    protected open fun configUiForDarkMode() {
        when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                background
                    .setTint(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.reader_edit_text_background_tint_dark_mode,
                            null
                        )
                    )
            }
            else -> {
            }
        }
    }

    companion object {
        private const val HORIZONTAL_PADDING = 60
        private const val VERTICAL_PADDING = 0
    }
}
