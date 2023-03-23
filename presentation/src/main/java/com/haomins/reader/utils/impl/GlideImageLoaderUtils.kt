package com.haomins.reader.utils.impl

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.executor.GlideExecutor
import com.bumptech.glide.module.AppGlideModule
import com.haomins.data.R
import com.haomins.reader.utils.ImageLoaderUtils
import java.net.URL
import javax.inject.Inject

@GlideModule
class ReaderGlideModule : AppGlideModule() {

    private fun getCustomGlideExecutor(): GlideExecutor {
        return GlideExecutor
            .newSourceBuilder()
            .setThreadCount(GlideImageLoaderUtils.THREAD_COUNT)
            .build()
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder
            .setLogLevel(Log.ERROR)
            .setSourceExecutor(getCustomGlideExecutor())
    }
}

class GlideImageLoaderUtils @Inject constructor(
    private val application: Application
) : ImageLoaderUtils {

    private val glide by lazy {
        Glide.with(application)
    }

    override fun loadIconImage(imageView: ImageView, href: String) {
        glide
            .asDrawable()
            .centerCrop()
            .load(Uri.parse(URL(href).toURI().toString()))
            .error(R.drawable.ic_broken_image)
            .into(imageView)
    }

    override fun loadPreviewImage(imageView: ImageView, urlString: String) {
        glide
            .asDrawable()
            .let {
                if (urlString.isNotEmpty()) {
                    it.centerCrop().load(Uri.parse(URL(urlString).toURI().toString()))
                } else {
                    it.centerInside().load(R.drawable.ic_broken_image)
                }
            }
            .error(R.drawable.ic_broken_image)
            .into(imageView)
    }

    companion object {
        internal const val THREAD_COUNT = 1
    }

}