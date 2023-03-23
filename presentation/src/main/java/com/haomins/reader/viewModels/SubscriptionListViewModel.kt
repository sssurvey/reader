package com.haomins.reader.viewModels

import android.app.Application
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.usecase.subscription.LoadAndSaveSubscriptionList
import com.haomins.model.entity.SubscriptionEntity
import com.haomins.reader.R
import com.haomins.reader.utils.image.ImageLoaderUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class SubscriptionListViewModel @Inject constructor(
    private val loadAndSaveSubscriptionList: LoadAndSaveSubscriptionList,
    private val imageLoaderUtils: ImageLoaderUtils,
    private val application: Application,
) : ViewModel() {

    companion object {
        private const val TAG = "SourceTitleListViewModel"
    }

    enum class TYPE {
        RSS_SOURCE,
        ALL_ITEMS_OPTION,
        ADD_SOURCE_OPTION,
        SETTINGS_OPTION,
        SUMMARY_OPTION,
    }

    data class SubscriptionListUi(
        val name: String,
        val sourceIconUrl: String? = null,
        val id: String,
        val type: TYPE,
    )

    private val _subscriptionListUiDataSet by lazy {
        MutableLiveData<List<SubscriptionListUi>>()
    }

    val subscriptionListUiDataSet: LiveData<List<SubscriptionListUi>> = _subscriptionListUiDataSet

    fun loadImageIcon(imageView: ImageView, url: String) {
        imageLoaderUtils.loadIconImage(imageView, url)
    }

    fun loadSubscriptionList() {
        loadAndSaveSubscriptionList.execute(
            object : DisposableSingleObserver<List<SubscriptionEntity>>() {
                override fun onSuccess(t: List<SubscriptionEntity>) {
                    _subscriptionListUiDataSet.postValue(populateSubSourceDataSet(t))
                    Log.d(TAG, "loadAndSaveSubscriptionList :: onSuccess")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "loadAndSaveSubscriptionList :: onError ${e.printStackTrace()}")
                }
            }
        )
    }

    override fun onCleared() {
        super.onCleared()
        loadAndSaveSubscriptionList.dispose()
    }

    private fun MutableList<SubscriptionListUi>.populateSourceListMenus() {
        add(
            SubscriptionListUi(
                name = application.resources.getString(R.string.menu_all_articles),
                type = TYPE.ALL_ITEMS_OPTION,
                id = ""
            )
        )
        add(
            SubscriptionListUi(
                name = application.resources.getString(R.string.menu_add_sources),
                type = TYPE.ADD_SOURCE_OPTION,
                id = ""
            )
        )
        add(
            SubscriptionListUi(
                name = application.resources.getString(R.string.menu_settings),
                type = TYPE.SETTINGS_OPTION,
                id = ""
            )
        )
    }

    private fun MutableList<SubscriptionListUi>.addSourceListSummary(count: Int) {
        add(SubscriptionListUi(name = "$count Subscriptions", type = TYPE.SUMMARY_OPTION, id = ""))
    }

    private fun populateSubSourceDataSet(subscriptionEntities: List<SubscriptionEntity>): MutableList<SubscriptionListUi> {
        return mutableListOf<SubscriptionListUi>().apply {
            populateSourceListMenus()
            subscriptionEntities.forEach {
                add(
                    SubscriptionListUi(
                        name = it.title,
                        sourceIconUrl = TheOldReaderService.DEFAULT_PROTOCOL + it.iconUrl,
                        id = it.id,
                        type = TYPE.RSS_SOURCE,
                    )
                )
            }
            addSourceListSummary(subscriptionEntities.size)
        }
    }

}