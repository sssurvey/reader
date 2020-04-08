package com.haomins.www.data.repositories

import android.content.SharedPreferences
import com.haomins.www.data.SharedPreferenceKey
import com.haomins.www.data.service.TheOldReaderService
import com.haomins.www.data.util.getValue
import com.haomins.www.data.db.AppDatabase
import com.haomins.www.data.db.RoomService
import com.haomins.www.data.db.entities.SubscriptionEntity
import com.haomins.www.data.models.subscription.SubscriptionItemModel
import com.haomins.www.data.models.subscription.SubscriptionSourceListResponseModel
import io.reactivex.Single
import javax.inject.Inject

class SourceSubscriptionListRepository @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val roomService: RoomService,
    private val sharedPreferences: SharedPreferences
) {

    fun loadSubList(): Single<SubscriptionSourceListResponseModel> {
        return theOldReaderService.loadSubscriptionSourceList(
            headerAuthValue = (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                    + sharedPreferences.getValue(SharedPreferenceKey.AUTH_CODE_KEY))
        )
    }

    fun retrieveSubListFromDB(): Single<List<SubscriptionEntity>> {
        return roomService.subscriptionDao().getAll()
    }

    fun saveSubListToDB(
        subscriptionSourceListResponseModel: SubscriptionSourceListResponseModel,
        clearOldTable: Boolean = true
    ) {
        if (clearOldTable) roomService.subscriptionDao().clearTable()
        val entityList =
            convertSubscriptionItemModelToEntity(subscriptionSourceListResponseModel.subscriptions)
        roomService.subscriptionDao().insertAll(*entityList.toTypedArray())
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