package com.haomins.reader.utils

import android.widget.ImageView

interface ImageLoaderUtils {

    fun loadPreviewImage(imageView: ImageView, urlString: String)

    fun loadIconImage(imageView: ImageView, href: String)

}