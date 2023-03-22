package com.haomins.data.repositories.local

import com.haomins.domain.repositories.ContactInfoLocalRepository
import javax.inject.Inject

class ContactInfoLocalDataStore @Inject constructor() : ContactInfoLocalRepository {

    companion object {
        private const val FEEDBACK_EMAIL = "youngmobileachiever@gmail.com"
        private const val THE_OLD_READ_WEBSITE = "www.theoldreader.com"
    }

    override fun getFeedbackEmail() = FEEDBACK_EMAIL

    override fun getTheOldReaderSite() = THE_OLD_READ_WEBSITE

}