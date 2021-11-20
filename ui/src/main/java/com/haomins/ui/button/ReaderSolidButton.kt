package com.haomins.ui.button

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatButton
import com.haomins.ui.R

class ReaderSolidButton : AppCompatButton {

    init {
        isAllCaps = false
        background =
            AppCompatResources.getDrawable(context, R.drawable.reader_solid_button_background)!!
        if (isEnabled) setClickableBackground()
        else setClickedBackground()
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        if (enabled) setClickableBackground()
        else setClickedBackground()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        return if (isEnabled && event != null && (
                    event.actionMasked == MotionEvent.ACTION_DOWN
                            || event.actionMasked == MotionEvent.ACTION_MOVE
                    )
        ) {
            setClickedBackground()
            true
        } else if (isEnabled && event != null && event.actionMasked == MotionEvent.ACTION_UP) {
            setClickableBackground()
            performClick()
            true
        } else if (isEnabled && event != null && event.actionMasked == MotionEvent.ACTION_CANCEL) {
            setClickableBackground()
            true
        } else {
            false
        }
    }

    private fun setClickableBackground() {
        background.alpha = OPAQUE_LEVEL_ENABLED
    }

    private fun setClickedBackground() {
        background.alpha = OPAQUE_LEVEL_DISABLED
    }

    companion object {
        private const val OPAQUE_LEVEL_DISABLED: Int = 255 / 2
        private const val OPAQUE_LEVEL_ENABLED: Int = 255
    }

}