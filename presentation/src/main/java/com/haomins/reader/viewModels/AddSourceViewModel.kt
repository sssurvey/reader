package com.haomins.reader.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.www.core.data.models.subscription.AddSubscriptionResponseModel
import com.haomins.www.core.repositories.AddSourceRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddSourceViewModel @Inject constructor(
    private val addSourceRepository: AddSourceRepository
) : ViewModel() {

    companion object {
        const val TAG = "AddSourceViewModel"
        private const val MEDIUM_RSS_FEED_BASE = "medium.com/feed/"
    }

    val isSourceAdded by lazy {
        MutableLiveData(false)
    }

    fun addSource(source: String) {
        addSourceRepository.addSource(source = source)
            .doOnSuccess(::checkIfSuccess)
            .doOnError(::printError)
            .subscribe()
    }

    fun addMediumSource(source: String) {
        addSourceRepository.addSource(source = MEDIUM_RSS_FEED_BASE + source)
            .doOnSuccess(::checkIfSuccess)
            .doOnError(::printError)
            .subscribe()
    }

    private fun printError(t: Throwable) {
        Log.d(TAG, "${t.printStackTrace()}")
    }

    private fun checkIfSuccess(addSubscriptionResponseModel: AddSubscriptionResponseModel) {
        when (addSubscriptionResponseModel.numResults) {
            1 -> isSourceAdded.postValue(true)
            0 -> isSourceAdded.postValue(false)
        }
    }
}