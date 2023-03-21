package com.haomins.data_model

enum class SharedPreferenceKey(val string: String) {
    AUTH_CODE_KEY("AUTH_CODE"),
    IS_DARK_MODE_ENABLED("IS_DARK_MODE_ENABLED"),
    OVERRIDE_DARK_MODE_SETTINGS("OVERRIDE_DARK_MODE_SETTINGS")
}