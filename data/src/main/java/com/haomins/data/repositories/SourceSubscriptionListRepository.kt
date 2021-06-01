package com.haomins.data.repositories

import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import com.haomins.data.mapper.entitymapper.SubscriptionEntityMapper
import com.haomins.data.model.SharedPreferenceKey
import com.haomins.data.model.entities.SubscriptionEntity
import com.haomins.data.model.responses.subscription.SubscriptionItemModel
import com.haomins.data.model.responses.subscription.SubscriptionSourceListResponseModel
import com.haomins.data.service.RoomService
import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.repositories.SourceSubscriptionListRepositoryContract
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SourceSubscriptionListRepository @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val subscriptionEntityMapper: SubscriptionEntityMapper,
    private val roomService: RoomService,
    private val sharedPreferences: SharedPreferences
) : SourceSubscriptionListRepositoryContract {

    override fun loadSubscriptionList(): Single<List<com.haomins.domain.model.entities.SubscriptionEntity>> {
        return theOldReaderService
            .loadSubscriptionSourceList(headerAuthValue = loadHeaderAuthValue())
            .flatMap { saveSubListToDB(it) }
            .flatMap { retrieveSubListFromDB() }
            .onErrorResumeNext { retrieveSubListFromDB() }
            .map {
                it.map {
                    subscriptionEntityMapper.dataModelToDomainModel(it)
                }
            }
    }

    private fun retrieveSubListFromDB(): Single<List<SubscriptionEntity>> {
        return roomService.subscriptionDao().getAll()
    }

    private fun saveSubListToDB(
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
                + sharedPreferences.getString(SharedPreferenceKey.AUTH_CODE_KEY.string, ""))
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

    @VisibleForTesting
    fun convertSubscriptionItemModelToEntityForTesting(subscriptions: ArrayList<SubscriptionItemModel>)
            : List<SubscriptionEntity> {
        return convertSubscriptionItemModelToEntity(subscriptions)
    }
}