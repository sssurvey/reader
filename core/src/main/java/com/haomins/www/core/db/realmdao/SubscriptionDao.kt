package com.haomins.www.core.db.realmdao

import com.haomins.www.core.data.realmentities.SubscriptionEntity
import io.realm.RealmConfiguration
import io.realm.RealmResults
import javax.inject.Inject

internal class SubscriptionDao @Inject constructor(
    realmConfiguration: RealmConfiguration
) : BaseDao<SubscriptionEntity>(realmConfiguration) {

    override fun getAll(): RealmResults<SubscriptionEntity> {
        return realm.where(SubscriptionEntity::class.java).findAllAsync()
    }

}