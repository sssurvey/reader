package com.haomins.data.model

import org.junit.Assert.assertTrue
import org.junit.Test

class SharedPreferenceKeyTest {

    @Test
    fun getString() {
        assertTrue(SharedPreferenceKey.AUTH_CODE_KEY.string == "AUTH_CODE")
        assertTrue(SharedPreferenceKey.IS_DARK_MODE_ENABLED.string == "IS_DARK_MODE_ENABLED")
    }
}