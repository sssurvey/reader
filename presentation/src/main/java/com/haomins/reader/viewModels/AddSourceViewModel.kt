package com.haomins.reader.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.www.core.data.models.subscription.AddSubscriptionResponseModel
import com.haomins.www.core.repositories.AddSourceRepository
import io.reactivex.disposables.CompositeDisposable
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

    private val compositeDisposable = CompositeDisposable()

    fun addSource(source: String) {
        compositeDisposable.add(addSourceRepository.addSource(source = source)
            .doOnSuccess(::checkIfSuccess)
            .subscribe({}, { printError(it) })
        )
    }

    fun addMediumSource(source: String) {
        compositeDisposable.add(addSourceRepository.addSource(source = MEDIUM_RSS_FEED_BASE + source)
            .doOnSuccess(::checkIfSuccess)
            .subscribe({}, { printError(it) })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
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