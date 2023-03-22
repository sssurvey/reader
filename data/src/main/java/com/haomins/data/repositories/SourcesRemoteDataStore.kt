package com.haomins.data.repositories

import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import com.haomins.data.db.dao.SubscriptionDao
import com.haomins.data.service.TheOldReaderService
import com.haomins.domain.repositories.SourcesRepository
import com.haomins.model.SharedPreferenceKey
import com.haomins.model.entity.SubscriptionEntity
import com.haomins.model.remote.subscription.SubscriptionItemModel
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SourcesRemoteDataStore @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val subscriptionDao: SubscriptionDao,
    private val sharedPreferences: SharedPreferences
) : SourcesRepository {

    override fun loadSubscriptionList(): Single<List<SubscriptionEntity>> {
        return theOldReaderService
            .loadSubscriptionSourceList(headerAuthValue = loadHeaderAuthValue())
            .map {
                it.subscriptions.convertSubscriptionItemModelToEntity().apply {
                    saveSubListToDatabase(this)
                }
            }
            .onErrorResumeNext { retrieveSubListFromDB() }
    }

    private fun retrieveSubListFromDB(): Single<List<SubscriptionEntity>> {
        return subscriptionDao.getAll()
    }

    private fun saveSubListToDatabase(
        entityList: List<SubscriptionEntity>,
    ) {
        with(subscriptionDao) {
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