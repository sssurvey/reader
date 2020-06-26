package com.haomins.www.core.db.realmdao

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmObject
import io.realm.RealmResults

internal abstract class BaseDao<T : RealmObject>(
    private val realmConfiguration: RealmConfiguration
) {

    protected lateinit var realm: Realm

    internal abstract fun getAll(): RealmResults<T>

    fun start() {
        realm = Realm.getInstance(realmConfiguration)
    }

    fun close() {
        if (!realm.isClosed) realm.close()
    }

    internal fun insertAll(vararg entities: T) {
        realm.executeTransaction { realm ->
            entities.forEach {
                realm.insertOrUpdate(it)
            }
        }
    }

    internal fun purgeDatabase() {
        realm.executeTransaction {
            it.deleteAll()
        }
    }

    internal fun dropTable(clazz: Class<T>) {
        realm.executeTransaction {
            it.delete(clazz)
        }
    }
}