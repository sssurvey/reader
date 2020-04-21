package com.haomins.reader.viewModels

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

    val isSourceAdded by lazy {
        MutableLiveData(false)
    }

    fun addSource(source: String) {
        addSourceRepository.addSource(source = source)
            .doOnSuccess(::checkIfSuccess)
            .subscribe()
    }

    private fun checkIfSuccess(addSubscriptionResponseModel: AddSubscriptionResponseModel) {
        when (addSubscriptionResponseModel.numResults) {
            1 -> isSourceAdded.postValue(true)
            0 -> isSourceAdded.postValue(false)
        }
    }
}