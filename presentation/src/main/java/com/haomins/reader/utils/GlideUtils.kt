package com.haomins.reader.utils

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
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@GlideModule
class ReaderGlideModule : AppGlideModule() {

    private fun getCustomGlideExecutor(): GlideExecutor {
        return GlideExecutor
            .newSourceBuilder()
            .setThreadCount(GlideUtils.THREAD_COUNT)
            .build()
    }

    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder
            .setLogLevel(Log.ERROR)
            .setSourceExecutor(getCustomGlideExecutor())
    }
}

@Singleton
class GlideUtils @Inject constructor(
    private val application: Application
) {

    private val glide by lazy {
        Glide.with(application)
    }

    fun loadIconImage(imageView: ImageView, href: String) {
        glide
            .asDrawable()
            .centerCrop()
            .load(Uri.parse(URL("$ICON_LOADING_URL$href").toURI().toString()))
            .error(R.drawable.ic_broken_image)
            .into(imageView)
    }

    fun loadPreviewImage(imageView: ImageView, urlString: String) {
        Log.d("TAG", "test 1")
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

    @Deprecated(
        message = "Out of date, creating URL in method now.",
        replaceWith = ReplaceWith("loadIconImage")
    )
    fun loadIconImage(imageView: ImageView, url: URL) {
        glide
            .asDrawable()
            .centerCrop()
            .load(Uri.parse(url.toURI().toString()))
            .error(R.drawable.ic_broken_image)
            .into(imageView)
    }

    companion object {
        internal const val THREAD_COUNT = 1
        private const val ICON_LOADING_URL = "https://www.google.com/s2/favicons?sz=64&domain_url="
    }

}