package com.haomins.www.model.service

import android.app.Application
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.haomins.www.model.R
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
                .load(Uri.parse(url.toURI().toString()))
                .error(R.drawable.ic_broken_image)
                .into(imageView)
    }

}