package com.haomins.model

import org.junit.Assert.assertTrue
import org.junit.Test

class SharedPreferenceKeyTest {

    @Test
    fun getString() {
        assertTrue(PreferenceKey.AUTH_CODE_KEY.string == "AUTH_CODE")
        assertTrue(PreferenceKey.IS_DARK_MODE_ENABLED.string == "IS_DARK_MODE_ENABLED")
    }
}