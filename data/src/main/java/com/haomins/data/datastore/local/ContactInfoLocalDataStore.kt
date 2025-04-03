package com.haomins.data.datastore.local

import com.haomins.domain.repositories.local.ContactInfoLocalRepository
import javax.inject.Inject

class ContactInfoLocalDataStore @Inject constructor() : ContactInfoLocalRepository {

    companion object {
        private const val FEEDBACK_EMAIL = "youngmobileachievers@gmail.com"
        private const val THE_OLD_READ_WEBSITE = "www.theoldreader.com"
    }

    override fun getFeedbackEmail() = FEEDBACK_EMAIL

    override fun getTheOldReaderSite() = THE_OLD_READ_WEBSITE

}