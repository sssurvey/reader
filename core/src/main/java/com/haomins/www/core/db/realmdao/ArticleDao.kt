package com.haomins.www.core.db.realmdao

import com.haomins.www.core.data.realmentities.ArticleEntity
import io.realm.RealmConfiguration
import io.realm.RealmResults
import javax.inject.Inject

internal class ArticleDao @Inject constructor(
    realmConfiguration: RealmConfiguration
) : BaseDao<ArticleEntity>(realmConfiguration) {

    override fun getAll(): RealmResults<ArticleEntity> {
        return realm.where(ArticleEntity::class.java).findAllAsync()
    }

}