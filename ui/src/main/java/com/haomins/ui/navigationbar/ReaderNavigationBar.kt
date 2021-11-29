package com.haomins.ui.navigationbar

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.haomins.ui.R
import kotlinx.android.synthetic.main.reader_navigation_bar.view.*

class ReaderNavigationBar : ConstraintLayout {

    init {
        inflate(context, R.layout.reader_navigation_bar, this)
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setOnClicks(
        addSourceOnClick: () -> Unit = { Log.d(TAG, "addSourceOnClick :: called") },
        allSourceOnClick: () -> Unit = { Log.d(TAG, "allSourceOnClick :: called") },
        searchSourceOnClick: () -> Unit = { Log.d(TAG, "searchSourceOnClick :: called") },
        savedSourceOnClick: () -> Unit = { Log.d(TAG, "savedSourceOnClick :: called") }
    ) {
        setAddSourceOnClick { addSourceOnClick.invoke() }
        setAllSourceOnClick { allSourceOnClick.invoke() }
        setSearchSourceOnClick { searchSourceOnClick.invoke() }
        setSavedSourceOnClick { savedSourceOnClick.invoke() }
    }

    private fun setAddSourceOnClick(onClickListener: OnClickListener) {
        with(add_source) {
            setOnClickListener(onClickListener)
            onClickEffect()
        }
    }

    private fun setAllSourceOnClick(onClickListener: OnClickListener) {
        with(all_source) {
            setOnClickListener(onClickListener)
            onClickEffect()
        }
    }

    private fun setSearchSourceOnClick(onClickListener: OnClickListener) {
        with(search_source) {
            setOnClickListener(onClickListener)
            onClickEffect()
        }
    }

    private fun setSavedSourceOnClick(onClickListener: OnClickListener) {
        with(saved_source) {
            setOnClickListener(onClickListener)
            onClickEffect()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun ImageView.onClickEffect() {
        this.setOnTouchListener { view, motionEvent ->
            when (motionEvent.actionMasked) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    (view as ImageView).setColorFilter(
                        ContextCompat.getColor(
                            context,
                            R.color.navigation_button_on_click_background
                        )
                    )
                    true
                }
                MotionEvent.ACTION_UP -> {
                    (view as ImageView).clearColorFilter()
                    performClick()
                    true
                }
                MotionEvent.ACTION_CANCEL -> {
                    (view as ImageView).clearColorFilter()
                    true
                }
                else -> false
            }
        }
    }

    companion object {
        private const val TAG = "ReaderNavigationBar"
    }
}