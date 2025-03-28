// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version com.haomins.buildsrc.Dependencies.ANDROID_PLUGIN_VERSION apply false
    id("com.android.library") version com.haomins.buildsrc.Dependencies.ANDROID_PLUGIN_VERSION apply false
    id("org.jetbrains.kotlin.android") version com.haomins.buildsrc.Dependencies.KOTLIN_VERSION apply false
    id("org.jetbrains.kotlin.jvm") version com.haomins.buildsrc.Dependencies.KOTLIN_VERSION apply false
    id("com.google.dagger.hilt.android") version com.haomins.buildsrc.Dependencies.HILT_VERSION apply false
}