package com.haomins.reader.viewModels

import android.app.Application
import android.util.Log
import android.widget.ImageView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.model.entities.SubscriptionEntity
import com.haomins.domain.usecase.source.LoadSubscriptionList
import com.haomins.reader.R
import com.haomins.reader.utils.GlideUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableSingleObserver
import java.net.URL
import javax.inject.Inject

@HiltViewModel
class SourceTitleListViewModel @Inject constructor(
    private val loadSubscriptionList: LoadSubscriptionList,
    private val glideUtils: GlideUtils,
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

    data class SourceListUi(
        val name: String,
        val sourceIconUrl: URL? = null,
        val id: String,
        val type: TYPE,
    )

    private val _sourceListUiDataSet by lazy {
        MutableLiveData<List<SourceListUi>>()
    }

    val sourceListUiDataSet: LiveData<List<SourceListUi>> = _sourceListUiDataSet

    fun loadImageIcon(imageView: ImageView, url: URL) {
        glideUtils.loadIconImage(imageView, url)
    }

    fun loadSourceSubscriptionList() {
        loadSubscriptionList.execute(
            object :
                DisposableSingleObserver<List<SubscriptionEntity>>() {
                override fun onSuccess(t: List<SubscriptionEntity>) {
                    _sourceListUiDataSet.postValue(populateSubSourceDataSet(t))
                    Log.d(TAG, "loadSubscriptionList :: onSuccess")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "loadSubscriptionList :: onError ${e.printStackTrace()}")
                }

            }
        )
    }

    private fun MutableList<SourceListUi>.populateSourceListMenus() {
        add(
            SourceListUi(
                name = application.resources.getString(R.string.menu_all_articles),
                type = TYPE.ALL_ITEMS_OPTION,
                id = ""
            )
        )
        add(
            SourceListUi(
                name = application.resources.getString(R.string.menu_add_sources),
                type = TYPE.ADD_SOURCE_OPTION,
                id = ""
            )
        )
        add(
            SourceListUi(
                name = application.resources.getString(R.string.menu_settings),
                type = TYPE.SETTINGS_OPTION,
                id = ""
            )
        )
    }

    private fun MutableList<SourceListUi>.addSourceListSummary(count: Int) {
        add(SourceListUi(name = "$count Subscriptions", type = TYPE.SUMMARY_OPTION, id = ""))
    }

    private fun populateSubSourceDataSet(subscriptionEntities: List<SubscriptionEntity>): MutableList<SourceListUi> {
        return mutableListOf<SourceListUi>().apply {
            populateSourceListMenus()
            subscriptionEntities.forEach {
                add(
                    SourceListUi(
                        name = it.title,
                        sourceIconUrl = URL(TheOldReaderService.DEFAULT_PROTOCOL + it.iconUrl),
                        id = it.id,
                        type = TYPE.RSS_SOURCE,
                    )
                )
            }
            addSourceListSummary(subscriptionEntities.size)
        }
    }

}