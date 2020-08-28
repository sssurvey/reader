package com.haomins.reader.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.reader.R
import com.haomins.www.model.data.models.subscription.AddSubscriptionResponseModel
import com.haomins.www.model.repositories.AddSourceRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class AddSourceViewModel @Inject constructor(
    private val addSourceRepository: AddSourceRepository,
    private val application: Application
) : ViewModel() {

    companion object {
        const val TAG = "AddSourceViewModel"
        private const val MEDIUM_RSS_FEED_BASE = "medium.com/feed/"
    }

    enum class AddSourceStatus {
        SUCCESS,
        FAIL,
        DEFAULT
    }

    val isSourceAdded by lazy {
        MutableLiveData(Pair(AddSourceStatus.DEFAULT, ""))
    }

    private val compositeDisposable = CompositeDisposable()

    fun addSource(source: String) {
        compositeDisposable.add(
            addSourceRepository.addSource(source = source)
                .doOnSuccess(::checkIfSuccess)
                .subscribe({}, { printError(it) })
        )
    }

    fun addMediumSource(source: String) {
        compositeDisposable.add(
            addSourceRepository.addSource(source = MEDIUM_RSS_FEED_BASE + source)
                .doOnSuccess(::checkIfSuccess)
                .subscribe({}, { printError(it) })
        )
    }

    override fun onCleared() {
        super.onCleared()
        isSourceAdded.postValue(Pair(AddSourceStatus.DEFAULT, ""))
        compositeDisposable.clear()
    }

    private fun printError(t: Throwable) {
        Log.d(TAG, "${t.printStackTrace()}")
    }

    private fun checkIfSuccess(addSubscriptionResponseModel: AddSubscriptionResponseModel) {
        when (addSubscriptionResponseModel.numResults) {
            1 -> isSourceAdded.postValue(
                Pair(
                    AddSourceStatus.SUCCESS,
                    application.applicationContext.getString(R.string.snackbar_feed_add_success)
                )
            )
            0 -> isSourceAdded.postValue(
                Pair(
                    AddSourceStatus.FAIL,
                    addSubscriptionResponseModel.error ?: "Error"
                )
            )
        }
    }
}