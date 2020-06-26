package com.haomins.www.core.db.realmdao

import com.haomins.www.core.data.realmentities.ArticleEntity
import io.realm.Realm
import io.realm.RealmResults
import javax.inject.Inject

internal class ArticleDao @Inject constructor(
    private val realm: Realm
) : BaseDao<ArticleEntity>(realm) {

    override fun getAll(): RealmResults<ArticleEntity> {
        return realm.where(ArticleEntity::class.java).findAllAsync()
    }

}