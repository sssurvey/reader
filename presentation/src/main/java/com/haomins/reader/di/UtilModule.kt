package com.haomins.reader.di

import com.haomins.reader.utils.image.GlideImageLoaderUtils
import com.haomins.reader.utils.image.ImageLoaderUtils
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UtilModule {

    @Binds
    @Singleton
    fun bindImageLoaderUtils(
        glideImageLoaderUtils: GlideImageLoaderUtils
    ): ImageLoaderUtils

}