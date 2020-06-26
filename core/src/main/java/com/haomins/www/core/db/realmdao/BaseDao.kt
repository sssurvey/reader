package com.haomins.www.core.db.realmdao

import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults

internal abstract class BaseDao<T : RealmObject>(
    private val realm: Realm
) {

    internal abstract fun getAll(): RealmResults<T>

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