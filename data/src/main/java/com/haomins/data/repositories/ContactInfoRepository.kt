package com.haomins.data.repositories

import com.haomins.domain.repositories.ContactInfoRepositoryContract
import javax.inject.Inject

class ContactInfoRepository @Inject constructor() : ContactInfoRepositoryContract {

    companion object {
        private const val FEEDBACK_EMAIL = "youngmobileachiever@gmail.com"
        private const val THE_OLD_READ_WEBSITE = "www.theoldreader.com"
    }

    override fun getFeedbackEmail() = FEEDBACK_EMAIL

    override fun getTheOldReaderSite() = THE_OLD_READ_WEBSITE

}