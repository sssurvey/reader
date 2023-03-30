package com.haomins.ui.toolbar

import android.content.Context
import android.content.res.Configuration
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.appbar.MaterialToolbar
import com.haomins.ui.R
import com.haomins.ui.databinding.ReaderToolbarBinding

class ReaderToolbar : MaterialToolbar {

    private val binding: ReaderToolbarBinding =
        ReaderToolbarBinding.inflate(LayoutInflater.from(context))

    init {
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
        with(binding.button1) {
            setText(text)
            if (hasOnClickListeners()) setOnClickListener(null)
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
        with(binding.button2) {
            setText(text)
            if (hasOnClickListeners()) setOnClickListener(null)
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
                binding.toolbarLayout.background.setTint(
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
            this@ReaderToolbar.binding.button1SelectedEffect.visibility = View.VISIBLE
            this@ReaderToolbar.binding.button2SelectedEffect.visibility = View.INVISIBLE
        } else {
            this@ReaderToolbar.binding.button1SelectedEffect.visibility = View.INVISIBLE
            this@ReaderToolbar.binding.button2SelectedEffect.visibility = View.VISIBLE
        }
    }

    companion object {

        private const val TAG = "ReaderToolbar"

    }

}