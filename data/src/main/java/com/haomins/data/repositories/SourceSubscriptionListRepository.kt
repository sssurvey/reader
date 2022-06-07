package com.haomins.data.repositories

import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import com.haomins.data.mapper.entitymapper.SubscriptionEntityMapper
import com.haomins.data.model.SharedPreferenceKey
import com.haomins.data.model.entities.SubscriptionEntity
import com.haomins.data.model.responses.subscription.SubscriptionItemModel
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
            .map {
                it.subscriptions.convertSubscriptionItemModelToEntity().apply {
                    saveSubListToDatabase(this)
                }
            }
            .onErrorResumeNext { retrieveSubListFromDB() }
            .map {
                it.map { subscriptionEntity ->
                    subscriptionEntityMapper.dataModelToDomainModel(subscriptionEntity)
                }
            }
    }

    private fun retrieveSubListFromDB(): Single<List<SubscriptionEntity>> {
        return roomService.subscriptionDao().getAll()
    }

    private fun saveSubListToDatabase(
        entityList: List<SubscriptionEntity>,
    ) {
        with(roomService.subscriptionDao()) {
            clearTable()
            insertAll(*entityList.toTypedArray())
        }
    }

    private fun loadHeaderAuthValue(): String {
        return (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                + sharedPreferences.getString(SharedPreferenceKey.AUTH_CODE_KEY.string, ""))
    }

    private fun ArrayList<SubscriptionItemModel>.convertSubscriptionItemModelToEntity()
            : List<SubscriptionEntity> {
        return map {
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
        return subscriptions.convertSubscriptionItemModelToEntity()
    }
}