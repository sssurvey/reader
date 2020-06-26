package com.haomins.www.core.db

import android.app.Application
import io.realm.Realm

object RealmUtil {

    internal const val CORE_REALM_NAME = "reader.core.realm"

    fun init(application: Application) {
        Realm.init(application)
    }
}
