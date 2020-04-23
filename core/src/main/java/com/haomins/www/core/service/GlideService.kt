package com.haomins.www.core.service

import android.app.Application
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GlideService @Inject constructor(
    private val application: Application
) {

    private val glide by lazy {
        Glide.with(application)
    }

    fun loadIconImage(imageView: ImageView, url: URL) {
        glide
            .asDrawable()
            .centerCrop()
            .load(url)
            .into(imageView)
    }

}