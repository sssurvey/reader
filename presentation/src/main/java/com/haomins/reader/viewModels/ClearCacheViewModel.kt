package com.haomins.reader.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.haomins.domain.usecase.util.ClearAndGetLocalDataSize
import com.haomins.domain.usecase.util.GetLocalDataSize
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class ClearCacheViewModel @Inject constructor(
    private val getLocalDataSize: GetLocalDataSize,
    private val clearAndGetLocalDataSize: ClearAndGetLocalDataSize
) : ViewModel() {

    fun getLocalDataSize(thenDo: (Long) -> Unit) {
        getLocalDataSize.execute(
            observer = object : DisposableSingleObserver<Long>() {
                override fun onSuccess(t: Long) {
                    Log.d(TAG, "getLocalDataSize :: onSuccess -> $t}")
                    thenDo.invoke(t)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "getLocalDataSize :: onError -> ${e.printStackTrace()}")
                }
            }
        )
    }

    fun clearLocalDataAndCalculateSize(thenDo: (Long) -> Unit) {
        clearAndGetLocalDataSize.execute(
            object : DisposableSingleObserver<Long>() {
                override fun onSuccess(t: Long) {
                    Log.d(TAG, "clearAndGetLocalDataSize :: onSuccess")
                    thenDo.invoke(t)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "clearAndGetLocalDataSize :: onError -> ${e.printStackTrace()}")
                }
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        getLocalDataSize.dispose()
        clearAndGetLocalDataSize.dispose()
    }

    companion object {
        private const val TAG = "ClearCacheViewModel"
    }
}