package com.haomins.www.core.db.realmdao

import com.haomins.www.core.data.realmentities.SubscriptionEntity
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults
import javax.inject.Inject

internal class SubscriptionDao @Inject constructor(
    private val realm: Realm
) : BaseDao<SubscriptionEntity>(realm) {

    override fun getAll(): RealmResults<SubscriptionEntity> {
        return realm.where(SubscriptionEntity::class.java).findAllAsync()
    }

}