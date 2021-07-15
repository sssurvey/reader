package com.haomins.reader.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import com.haomins.reader.R
import javax.inject.Inject

class DisclosureViewModel @Inject constructor(
    private val application: Application
) : ViewModel() {

    fun loadDisclosureTitle(): String {
        return application.resources.getString(R.string.disclosure_title)
    }

    fun loadDisclosurePolicyContent(): String {
        return application.resources.getString(R.string.placeholder_text)
    }

    fun loadDisclosureEmail(): String {
        return application.resources.getString(R.string.placeholder_email)
    }

    fun loadDisclosurePhone(): String {
        return application.resources.getString(R.string.placeholder_phone_number)
    }

    fun loadDisclosureWebsite(): String {
        return application.resources.getString(R.string.placeholder_website)
    }
}