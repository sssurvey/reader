package com.haomins.buildsrc

object Dependencies {

    const val KOTLIN_VERSION = "1.9.0"
    const val HILT_VERSION = "2.50"
    const val ANDROID_PLUGIN_VERSION = "8.9.1"

    private const val ANDROIDX_LIFECYCLE_VERSION = "2.5.1"
    private const val ANDROID_ARCH_LIFECYCLE_VERSION = "1.1.1"
    private const val RETROFIT_2_VERSION = "2.4.0"
    private const val ROOM_VERSION = "2.5.1"
    private const val GLIDE_VERSION = "4.11.0"
    private const val RXJAVA_VERSION = "2.2.6"
    private const val MOCKITO_KOTLIN_2_VERSION = "4.1.0"
    private const val JUNIT_VERSION = "4.12"
    private const val MOCKITO_INLINE_VERSION = "5.2.0"
    private const val MATERIAL_VERSION = "1.1.0"
    private const val KTX_VERSION = "1.7.0"
    private const val APP_COMPAT_VERSION = "1.4.0"
    private const val CONSTRAINT_LAYOUT_VERSION = "2.1.2"
    private const val ESPRESSO_VERSION = "3.4.0"
    private const val ANDROIDX_TEST_JUNIT_VERSION = "1.1.5"
    private const val PAGING_VERSION = "3.1.1"

    // TODO: [ISSUE-182] remove the JvmField once migrated to kts for all build scrips.
    @JvmField
    val appDependencies = listOf(
        "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$KOTLIN_VERSION",
        "com.squareup.okhttp3:logging-interceptor:3.8.0",
        "androidx.appcompat:appcompat:$APP_COMPAT_VERSION",
        "androidx.core:core-ktx:$KTX_VERSION",
        "androidx.constraintlayout:constraintlayout:$CONSTRAINT_LAYOUT_VERSION",
        "androidx.lifecycle:lifecycle-viewmodel-ktx:$ANDROIDX_LIFECYCLE_VERSION",
        "androidx.lifecycle:lifecycle-livedata-ktx:$ANDROIDX_LIFECYCLE_VERSION",
        "androidx.navigation:navigation-fragment-ktx:$ANDROIDX_LIFECYCLE_VERSION",
        "io.reactivex.rxjava2:rxjava:$RXJAVA_VERSION",
        "io.reactivex.rxjava2:rxandroid:2.1.1",
        "android.arch.lifecycle:extensions:$ANDROID_ARCH_LIFECYCLE_VERSION",
        "androidx.preference:preference-ktx:$ANDROID_ARCH_LIFECYCLE_VERSION",
        "androidx.recyclerview:recyclerview:1.1.0",
        "androidx.webkit:webkit:1.6.1",
        "androidx.cardview:cardview:1.0.0",
        "androidx.viewpager2:viewpager2:1.0.0",
        "com.google.android.material:material:$MATERIAL_VERSION",
        "com.github.bumptech.glide:glide:$GLIDE_VERSION",
        "com.github.bumptech.glide:annotations:$GLIDE_VERSION",
        "com.google.dagger:hilt-android:$HILT_VERSION",
        "androidx.paging:paging-runtime:$PAGING_VERSION"
    )

    @JvmField
    val appKaptDependencies = listOf(
        "android.arch.lifecycle:compiler:$ANDROID_ARCH_LIFECYCLE_VERSION",
        "com.github.bumptech.glide:compiler:$GLIDE_VERSION",
        "com.google.dagger:hilt-compiler:$HILT_VERSION"
    )

    @JvmField
    val domainDependencies = listOf(
        "javax.inject:javax.inject:1",
        "io.reactivex.rxjava2:rxjava:$RXJAVA_VERSION",
        "org.jetbrains.kotlin:kotlin-stdlib:$KOTLIN_VERSION",
        "androidx.paging:paging-common-ktx:$PAGING_VERSION",
        "org.jsoup:jsoup:1.14.3",
    )

    @JvmField
    val dataDependencies = listOf(
        "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$KOTLIN_VERSION",
        "com.squareup.retrofit2:retrofit:$RETROFIT_2_VERSION",
        "io.reactivex.rxjava2:rxjava:$RXJAVA_VERSION",
        "androidx.room:room-runtime:$ROOM_VERSION",
        "androidx.room:room-rxjava2:$ROOM_VERSION",
        "com.google.dagger:hilt-android:$HILT_VERSION",
        "androidx.paging:paging-runtime:$PAGING_VERSION",
        "androidx.paging:paging-rxjava2:$PAGING_VERSION",
        "androidx.room:room-paging:$ROOM_VERSION",
    )

    @JvmField
    val dataKaptDependencies = listOf(
        "androidx.room:room-compiler:$ROOM_VERSION",
        "com.google.dagger:hilt-compiler:$HILT_VERSION"
    )

    @JvmField
    val modelDependencies = listOf(
        "com.google.code.gson:gson:2.8.5",
        "androidx.room:room-common:$ROOM_VERSION",
        "javax.inject:javax.inject:1",
    )

    @JvmField
    val uiDependencies = listOf(
        "androidx.constraintlayout:constraintlayout:$CONSTRAINT_LAYOUT_VERSION",
        "com.google.android.material:material:$MATERIAL_VERSION",
        "androidx.core:core-ktx:$KTX_VERSION",
        "androidx.appcompat:appcompat:$APP_COMPAT_VERSION",
    )

    @JvmField
    val appTestImplementation = listOf(
        "junit:junit:$JUNIT_VERSION"
    )

    @JvmField
    val domainTestImplementation = listOf(
        "junit:junit:$JUNIT_VERSION",
        "org.mockito.kotlin:mockito-kotlin:$MOCKITO_KOTLIN_2_VERSION",
        "org.mockito:mockito-inline:$MOCKITO_INLINE_VERSION"
    )

    @JvmField
    val appAndroidTestImplementation = listOf(
        "androidx.test.ext:junit:$ANDROIDX_TEST_JUNIT_VERSION",
        "androidx.test.espresso:espresso-core:$ESPRESSO_VERSION",
        "androidx.test.espresso:espresso-contrib:$ESPRESSO_VERSION",
        "org.junit.jupiter:junit-jupiter-api:5.8.2"
    )

    @JvmField
    val dataTestImplementation = listOf(
        "junit:junit:$JUNIT_VERSION",
        "org.mockito.kotlin:mockito-kotlin:$MOCKITO_KOTLIN_2_VERSION",
        "org.mockito.kotlin:mockito-kotlin:$MOCKITO_KOTLIN_2_VERSION",
        "org.mockito:mockito-inline:$MOCKITO_INLINE_VERSION"
    )

    @JvmField
    val dataAndroidTestImplementation = listOf(
        "androidx.test.ext:junit:$ANDROIDX_TEST_JUNIT_VERSION",
        "androidx.test:runner:1.5.2",
        "androidx.room:room-testing:$ROOM_VERSION",
        "junit:junit:$JUNIT_VERSION"
    )

    @JvmField
    val dataModelTestDependencies = listOf(
        "junit:junit:$JUNIT_VERSION",
        "org.mockito.kotlin:mockito-kotlin:$MOCKITO_KOTLIN_2_VERSION",
        "org.mockito:mockito-inline:$MOCKITO_INLINE_VERSION"
    )

    val wiringDependencies = listOf(
        "com.google.code.gson:gson:2.8.5",
        "com.squareup.retrofit2:retrofit:$RETROFIT_2_VERSION",
        "com.squareup.retrofit2:converter-gson:$RETROFIT_2_VERSION",
        "com.squareup.retrofit2:adapter-rxjava:$RETROFIT_2_VERSION",
        "io.reactivex.rxjava2:rxjava:$RXJAVA_VERSION",
        "io.reactivex.rxjava2:rxandroid:2.1.1",
        "com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0",
        "com.squareup.okhttp3:logging-interceptor:3.8.0",
        "androidx.room:room-runtime:$ROOM_VERSION",
        "com.google.dagger:hilt-android:$HILT_VERSION",
    )

    val wiringKaptDependencies = listOf(
        "com.google.dagger:hilt-compiler:$HILT_VERSION"
    )

}