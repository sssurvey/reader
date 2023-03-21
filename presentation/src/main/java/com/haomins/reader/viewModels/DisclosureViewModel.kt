package com.haomins.reader.viewModels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import com.haomins.domain_model.DisclosureInfo
import com.haomins.domain.usecase.disclosure.LoadDisclosureContent
import com.haomins.reader.R
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class DisclosureViewModel @Inject constructor(
    private val application: Application,
    private val loadDisclosureContent: LoadDisclosureContent
) : ViewModel() {

    companion object {
        private const val TAG = "DisclosureViewModel"
    }

    fun loadDisclosure(thenDo: (com.haomins.domain_model.DisclosureInfo) -> Unit) {
        loadDisclosureContent.execute(
            object : DisposableSingleObserver<com.haomins.domain_model.DisclosureInfo>() {
                override fun onSuccess(t: com.haomins.domain_model.DisclosureInfo) {
                    thenDo(t)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "loadDisclosureContent :: onError $e")
                }
            }
        )
    }

    fun loadDisclosureTitle(): String {
        return application.resources.getString(R.string.disclosure_title)
    }

}