package com.haomins.reader.viewModels

import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.data.model.entities.SubscriptionEntity
import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.usecase.source.LoadSubscriptionList
import com.haomins.reader.utils.GlideUtils
import io.reactivex.observers.DisposableSingleObserver
import java.net.URL
import javax.inject.Inject

class SourceTitleListViewModel @Inject constructor(
    private val loadSubscriptionList: LoadSubscriptionList,
    private val glideUtils: GlideUtils
) : ViewModel() {

    companion object {
        private const val TAG = "SourceTitleListViewModel"
    }

    private val _sourceListUiDataSet by lazy {
        MutableLiveData<List<Pair<String, URL>>>()
    }

    val sourceListUiDataSet: LiveData<List<Pair<String, URL>>> = _sourceListUiDataSet

    private lateinit var sourceListData: List<SubscriptionEntity>

    fun getSubSourceId(position: Int): String {
        return sourceListData[position].id
    }

    fun loadImageIcon(imageView: ImageView, url: URL) {
        glideUtils.loadIconImage(imageView, url)
    }

    fun loadSourceSubscriptionList() {
        loadSubscriptionList.execute(
            object :
                DisposableSingleObserver<List<com.haomins.domain.model.entities.SubscriptionEntity>>() {
                override fun onSuccess(t: List<com.haomins.domain.model.entities.SubscriptionEntity>) {
                    _sourceListUiDataSet.postValue(populateSubSourceDataSet(t))
                    Log.d(TAG, "loadSubscriptionList :: onSuccess")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "loadSubscriptionList :: onError ${e.printStackTrace()}")
                }

            }
        )
    }

    private fun populateSubSourceDataSet(subscriptionEntities: List<com.haomins.domain.model.entities.SubscriptionEntity>): MutableList<Pair<String, URL>> {
        return mutableListOf<Pair<String, URL>>().apply {
            subscriptionEntities.forEach {
                add(it.title to URL(TheOldReaderService.DEFAULT_PROTOCOL + it.iconUrl))
            }
        }
    }

}