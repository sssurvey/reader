package com.haomins.reader.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.domain.model.AddSourceResponseModel
import com.haomins.domain.usecase.addsource.AddNewRssSource
import com.haomins.reader.R
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

class AddSourceViewModel @Inject constructor(
    private val addNewRssSource: AddNewRssSource,
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

    fun addSource(source: String) {
        addNewRssSource.execute(
            params = AddNewRssSource.forAddNewRssSource(source = source),
            observer = object: DisposableSingleObserver<AddSourceResponseModel>() {
                override fun onSuccess(t: AddSourceResponseModel) {
                    checkIfSuccess(t)
                }

                override fun onError(e: Throwable) {
                    printError(e)
                }
            }
        )
//        compositeDisposable.add(
//            addSourceRepository.addSource(source = source)
//                .doOnSuccess(::checkIfSuccess)
//                .subscribe({}, { printError(it) })
//        )
    }

    fun addMediumSource(source: String) {
        addNewRssSource.execute(
            params = AddNewRssSource.forAddNewRssSource(source = MEDIUM_RSS_FEED_BASE + source),
            observer = object: DisposableSingleObserver<AddSourceResponseModel>() {
                override fun onSuccess(t: AddSourceResponseModel) {
                    checkIfSuccess(t)
                }

                override fun onError(e: Throwable) {
                    printError(e)
                }
            }
        )
//        compositeDisposable.add(
//            addSourceRepository.addSource(source = MEDIUM_RSS_FEED_BASE + source)
//                .doOnSuccess(::checkIfSuccess)
//                .subscribe({}, { printError(it) })
//        )
    }

    override fun onCleared() {
        super.onCleared()
        isSourceAdded.postValue(Pair(AddSourceStatus.DEFAULT, ""))
        addNewRssSource.dispose()
    }

    private fun printError(t: Throwable) {
        Log.d(TAG, "${t.printStackTrace()}")
    }

    private fun checkIfSuccess(addSourceResponseModel: AddSourceResponseModel) {
        when (addSourceResponseModel.result) {
            1 -> isSourceAdded.postValue(
                Pair(
                    AddSourceStatus.SUCCESS,
                    application.applicationContext.getString(R.string.snackbar_feed_add_success)
                )
            )
            0 -> isSourceAdded.postValue(
                Pair(
                    AddSourceStatus.FAIL,
                    addSourceResponseModel.error ?: "Error"
                )
            )
        }
    }
}