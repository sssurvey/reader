package com.haomins.ui.navigationbar

import android.content.Context
import android.util.AttributeSet
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

    fun setAddSourceOnClick(onClickListener: OnClickListener) {
        add_source.setOnClickListener(onClickListener)
    }

    fun setAllSourceOnClick(onClickListener: OnClickListener) {
        all_source.setOnClickListener(onClickListener)
    }

    fun setSearchSourceOnClick(onClickListener: OnClickListener) {
        search_source.setOnClickListener(onClickListener)
    }

    fun setSavedSourceOnClick(onClickListener: OnClickListener) {
        saved_source.setOnClickListener(onClickListener)
    }

}