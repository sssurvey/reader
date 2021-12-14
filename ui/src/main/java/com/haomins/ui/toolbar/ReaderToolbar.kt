package com.haomins.ui.toolbar

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.appbar.MaterialToolbar
import com.haomins.ui.R
import kotlinx.android.synthetic.main.reader_toolbar.view.*

class ReaderToolbar : MaterialToolbar {

    init {
        inflate(context, R.layout.reader_toolbar, this)
        initializeButton1().also { highlightSelectedButtons(isButtonOneSelected = true) }
        initializeButton2()
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun initializeButton1(
        text: String = "Button 1",
        onClick: () -> Unit = { Log.d(TAG, "Button 1 :: onClick") }
    ) {
        with(button_1) {
            setText(text)
            if (button_1.hasOnClickListeners()) button_1.setOnClickListener(null)
            setOnClickListener {
                onClick.invoke()
                highlightSelectedButtons(true)
            }
        }
    }

    fun initializeButton2(
        text: String = "Button 2",
        onClick: () -> Unit = { Log.d(TAG, "Button 2 :: onClick") }
    ) {
        with(button_2) {
            setText(text)
            if (button_2.hasOnClickListeners()) button_2.setOnClickListener(null)
            setOnClickListener {
                onClick.invoke()
                highlightSelectedButtons(false)
            }
        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        configUiForDarkMode()
    }

    private fun configUiForDarkMode() {
        when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> {
                toolbar_layout.background.setTint(
                        ResourcesCompat.getColor(
                            resources,
                            R.color.reader_toolbar_background_tint_dark_mode,
                            null
                        )
                    )
            }
            else -> {
            }
        }
    }

    private fun highlightSelectedButtons(isButtonOneSelected: Boolean) {
        if (isButtonOneSelected) {
            this@ReaderToolbar.button_1_selected_effect.visibility = View.VISIBLE
            this@ReaderToolbar.button_2_selected_effect.visibility = View.INVISIBLE
        } else {
            this@ReaderToolbar.button_1_selected_effect.visibility = View.INVISIBLE
            this@ReaderToolbar.button_2_selected_effect.visibility = View.VISIBLE
        }
    }

    companion object {

        private const val TAG = "ReaderToolbar"

    }

}