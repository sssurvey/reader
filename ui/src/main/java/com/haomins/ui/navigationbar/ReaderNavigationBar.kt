package com.haomins.ui.navigationbar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.haomins.ui.R
import com.haomins.ui.databinding.ReaderNavigationBarBinding

class ReaderNavigationBar : ConstraintLayout {

    private val binding: ReaderNavigationBarBinding =
        ReaderNavigationBarBinding.inflate(LayoutInflater.from(context))

    init {
        setCurrentlySelected(1) // Default to 1 which is home screen icon
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setCurrentlySelected(selection: Int) {
        with(binding) {
            when (selection) {
                0 -> {
                    addSource.drawable.setTint(Color.WHITE)
                    allSource.drawable.setTintList(null)
                    searchSource.drawable.setTintList(null)
                    savedSource.drawable.setTintList(null)
                }
                1 -> {
                    addSource.drawable.setTintList(null)
                    allSource.drawable.setTint(Color.WHITE)
                    searchSource.drawable.setTintList(null)
                    savedSource.drawable.setTintList(null)
                }
                2 -> {
                    addSource.drawable.setTintList(null)
                    allSource.drawable.setTintList(null)
                    searchSource.drawable.setTint(Color.WHITE)
                    savedSource.drawable.setTintList(null)
                }
                3 -> {
                    addSource.drawable.setTintList(null)
                    allSource.drawable.setTintList(null)
                    searchSource.drawable.setTintList(null)
                    savedSource.drawable.setTint(Color.WHITE)
                }
                else -> {
                    addSource.drawable.setTintList(null)
                    allSource.drawable.setTintList(null)
                    searchSource.drawable.setTintList(null)
                    savedSource.drawable.setTintList(null)
                    Log.d(TAG, "Navigation Selection Out of Range")
                }
            }
        }
    }

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
        with(binding.addSource) {
            setOnClickListener(onClickListener)
            onClickEffect()
        }
    }

    private fun setAllSourceOnClick(onClickListener: OnClickListener) {
        with(binding.allSource) {
            setOnClickListener(onClickListener)
            onClickEffect()
        }
    }

    private fun setSearchSourceOnClick(onClickListener: OnClickListener) {
        with(binding.searchSource) {
            setOnClickListener(onClickListener)
            onClickEffect()
        }
    }

    private fun setSavedSourceOnClick(onClickListener: OnClickListener) {
        with(binding.savedSource) {
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