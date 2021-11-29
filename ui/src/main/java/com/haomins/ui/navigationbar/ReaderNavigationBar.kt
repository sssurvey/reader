package com.haomins.ui.navigationbar

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.constraintlayout.widget.ConstraintLayout
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
        add_source.setOnClickListener(onClickListener)
    }

    private fun setAllSourceOnClick(onClickListener: OnClickListener) {
        all_source.setOnClickListener(onClickListener)
    }

    private fun setSearchSourceOnClick(onClickListener: OnClickListener) {
        search_source.setOnClickListener(onClickListener)
    }

    private fun setSavedSourceOnClick(onClickListener: OnClickListener) {
        saved_source.setOnClickListener(onClickListener)
    }

    companion object {
        private const val TAG = "ReaderNavigationBar"
    }
}