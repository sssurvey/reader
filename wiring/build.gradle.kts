plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
}

android {
    compileSdk = com.haomins.buildsrc.Configs.TARGET_SDK_VERSION

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility(com.haomins.buildsrc.Configs.JAVA_VERSION)
        targetCompatibility(com.haomins.buildsrc.Configs.JAVA_VERSION)
    }

    kotlinOptions {
        jvmTarget = com.haomins.buildsrc.Configs.JAVA_VERSION.toString()
    }

    namespace = "com.haomins.wiring"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":data"))
    com.haomins.buildsrc.Dependencies.wiringDependencies
        .forEach { implementation(it) }
    com.haomins.buildsrc.Dependencies.wiringKaptDependencies
        .forEach { kapt(it) }
}