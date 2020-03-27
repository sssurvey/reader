package com.haomins.reader.repositories

import android.content.SharedPreferences
import com.haomins.reader.TheOldReaderService
import com.haomins.reader.data.AppDatabase
import com.haomins.reader.data.entities.SubscriptionEntity
import com.haomins.reader.models.subscription.SubscriptionItemModel
import com.haomins.reader.models.subscription.SubscriptionSourceListResponseModel
import com.haomins.reader.utils.getValue
import com.haomins.reader.viewModels.LoginViewModel
import io.reactivex.Single
import javax.inject.Inject

class SourceSubscriptionListRepository @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val appDatabase: AppDatabase,
    private val sharedPreferences: SharedPreferences
) {

    fun loadSubList(): Single<SubscriptionSourceListResponseModel> {
        return theOldReaderService.loadSubscriptionSourceList(
            headerAuthValue = (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                    + sharedPreferences.getValue(LoginViewModel.AUTH_CODE_KEY))
        )
    }

    fun retrieveSubListFromDB(): Single<List<SubscriptionEntity>> {
        return appDatabase.subscriptionDao().getAll()
    }

    fun saveSubListToDB(
        subscriptionSourceListResponseModel: SubscriptionSourceListResponseModel,
        clearOldTable: Boolean = true
    ) {
        if (clearOldTable) appDatabase.subscriptionDao().clearTable()
        val entityList =
            convertSubscriptionItemModelToEntity(subscriptionSourceListResponseModel.subscriptions)
        appDatabase.subscriptionDao().insertAll(*entityList.toTypedArray())
    }

    private fun convertSubscriptionItemModelToEntity(subscriptions: ArrayList<SubscriptionItemModel>): List<SubscriptionEntity> {
        return subscriptions.map {
            SubscriptionEntity(
                id = it.id,
                title = it.title,
                sortId = it.sortId,
                firstItemMilSec = it.firstItemMilSec,
                url = it.url,
                htmlUrl = it.htmlUrl,
                iconUrl = it.iconUrl
            )
        }
    }
}