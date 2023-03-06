package com.haomins.reader.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.domain.model.responses.AddSourceResponseModel
import com.haomins.domain.usecase.addsource.AddNewSource
import com.haomins.reader.R
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class AddSourceViewModel @Inject constructor(
    private val addNewSource: AddNewSource,
    private val application: Application
) : ViewModel() {

    companion object {
        const val TAG = "AddSourceViewModel"
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
        addNewSource.execute(
            params = AddNewSource.forAddNewRssSource(source = source),
            observer = object : DisposableSingleObserver<AddSourceResponseModel>() {
                override fun onSuccess(t: AddSourceResponseModel) {
                    checkIfSuccess(t)
                }

                override fun onError(e: Throwable) {
                    printError(e)
                }
            }
        )
    }

    fun addMediumSource(source: String) {
        addNewSource.execute(
            params = AddNewSource.forAddingNewMediumSource(source = source),
            observer = object : DisposableSingleObserver<AddSourceResponseModel>() {
                override fun onSuccess(t: AddSourceResponseModel) {
                    checkIfSuccess(t)
                }

                override fun onError(e: Throwable) {
                    printError(e)
                }
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        isSourceAdded.postValue(Pair(AddSourceStatus.DEFAULT, ""))
        addNewSource.dispose()
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