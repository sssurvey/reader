package com.haomins.buildsrc

import org.gradle.api.JavaVersion

object Configs {
    const val MIN_SDK_VERSION = 26
    const val TARGET_SDK_VERSION = 35

    // TODO: [ISSUE-182] remove the JvmField once migrated to kts for all build scrips.
    @JvmField
    val JAVA_VERSION = JavaVersion.VERSION_21
}