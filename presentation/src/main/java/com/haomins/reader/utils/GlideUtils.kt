package com.haomins.reader.utils

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.haomins.data.R
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@GlideModule
class ReaderGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setLogLevel(Log.ERROR)
    }
}

@Singleton
class GlideUtils @Inject constructor(
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