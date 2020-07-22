package com.haomins.www.core.repositories

import android.content.SharedPreferences
import android.widget.ImageView
import com.haomins.www.core.data.SharedPreferenceKey
import com.haomins.www.core.data.entities.SubscriptionEntity
import com.haomins.www.core.data.models.subscription.SubscriptionItemModel
import com.haomins.www.core.data.models.subscription.SubscriptionSourceListResponseModel
import com.haomins.www.core.policy.RxSchedulingPolicy
import com.haomins.www.core.service.GlideService
import com.haomins.www.core.service.RoomService
import com.haomins.www.core.service.TheOldReaderService
import com.haomins.www.core.util.getString
import io.reactivex.Single
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SourceSubscriptionListRepository @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val roomService: RoomService,
    private val glideService: GlideService,
    private val sharedPreferences: SharedPreferences,
    private val defaultSchedulingPolicy: RxSchedulingPolicy
) {

    fun loadSubList(): Single<SubscriptionSourceListResponseModel> {
        with(defaultSchedulingPolicy) {
            return theOldReaderService.loadSubscriptionSourceList(
                headerAuthValue = loadHeaderAuthValue()
            ).defaultSchedulingPolicy()
        }
    }

    fun loadIconImage(imageView: ImageView, url: URL) {
        glideService.loadIconImage(imageView, url)
    }

    fun retrieveSubListFromDB(): Single<List<SubscriptionEntity>> {
        return roomService.subscriptionDao().getAll()
    }

    fun saveSubListToDB(
        subscriptionSourceListResponseModel: SubscriptionSourceListResponseModel,
        clearOldTable: Boolean = true
    ): Single<Unit> {
        return Single.fromCallable {
            if (clearOldTable) roomService.subscriptionDao().clearTable()
            val entityList =
                convertSubscriptionItemModelToEntity(subscriptionSourceListResponseModel.subscriptions)
            roomService.subscriptionDao().insertAll(*entityList.toTypedArray())
        }
    }

    private fun loadHeaderAuthValue(): String {
        return (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                + sharedPreferences.getString(SharedPreferenceKey.AUTH_CODE_KEY))
    }

    private fun convertSubscriptionItemModelToEntity(subscriptions: ArrayList<SubscriptionItemModel>)
            : List<SubscriptionEntity> {
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