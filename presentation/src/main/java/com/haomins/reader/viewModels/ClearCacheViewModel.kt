package com.haomins.reader.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.haomins.domain.usecase.util.GetLocalDataSize
import com.haomins.reader.view.fragments.settings.ClearCacheFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class ClearCacheViewModel @Inject constructor(
    private val getLocalDataSize: GetLocalDataSize
): ViewModel() {

    fun getLocalDataSize(thenDo: (Long) -> Unit) {
        getLocalDataSize.execute(
            observer = object : DisposableSingleObserver<Long>() {
                override fun onSuccess(t: Long) {
                    Log.d(ClearCacheFragment.TAG, "getLocalDataSize :: onSuccess -> $t}")
                    thenDo.invoke(t)
                }

                override fun onError(e: Throwable) {
                    Log.e(ClearCacheFragment.TAG, "getLocalDataSize :: onError -> ${e.printStackTrace()}")
                }
            }
        )
    }

}