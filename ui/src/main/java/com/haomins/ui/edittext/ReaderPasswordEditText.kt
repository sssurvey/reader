package com.haomins.ui.edittext

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.InputType
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import com.haomins.ui.R

class ReaderPasswordEditText : ReaderEditText, View.OnTouchListener {

    /**
     * ShowPasswordIcon: Pair<Boolean, Drawable>:
     * @param first: Boolean if false meaning password masked
     * @param second: Drawable represents the current icon loaded
     */
    private var showPasswordIconPair: Pair<Boolean, Drawable>

    init {
        setOnTouchListener(this)
        inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        showPasswordIconPair =
            false to AppCompatResources.getDrawable(context, R.drawable.ic_eyes_closed)!!
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        text?.let {
            if (it.isNotEmpty()) {
                showButton()
            } else {
                hideButton()
            }
        }
    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        compoundDrawablesRelative[2]?.let {
            if (isDrawableClicked(event)) {
                return when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        toggleShowPasswordIcon()
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }
        return false
    }

    private fun isDrawableClicked(motionEvent: MotionEvent?): Boolean {
        val drawableStart: Float?
        val drawableEnd: Float?

        return when (layoutDirection) {
            View.LAYOUT_DIRECTION_RTL -> {
                drawableEnd = (showPasswordIconPair.second.intrinsicWidth + paddingStart).toFloat()
                motionEvent?.x ?: drawableEnd < drawableEnd
            }
            View.LAYOUT_DIRECTION_LTR -> {
                drawableStart =
                    (width - showPasswordIconPair.second.intrinsicWidth - paddingStart).toFloat()
                motionEvent?.x ?: drawableStart > drawableStart
            }
            else -> {
                false
            }
        }
    }

    private fun toggleShowPasswordIcon() {
        showPasswordIconPair = if (showPasswordIconPair.first) {
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            false to AppCompatResources.getDrawable(context, R.drawable.ic_eyes_closed)!!
        } else {
            inputType = InputType.TYPE_CLASS_TEXT
            true to AppCompatResources.getDrawable(context, R.drawable.ic_eyes_open)!!
        }
        showButton()
    }

    private fun showButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            null,
            null,
            showPasswordIconPair.second,
            null
        )
    }

    private fun hideButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(
            null,
            null,
            null,
            null
        )
    }

}