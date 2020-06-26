package com.haomins.www.core.data.realmentities

import io.realm.RealmObject
import io.realm.annotations.Ignore
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField

open class SubscriptionEntity(

    @PrimaryKey
    @RealmField(name = "id")
    var id: String = "",

    @RealmField(name = "title")
    var title: String = "",

    @RealmField(name = "sort_id")
    var sortId: String = "",

    @RealmField(name = "first_item_millisecond")
    var firstItemMilSec: String = "",

    @RealmField(name = "url")
    var url: String = "",

    @RealmField(name = "html_url")
    var htmlUrl: String = "",

    @RealmField(name = "icon_url")
    var iconUrl: String = "",

    @Ignore
    var categories: Array<String> = emptyArray()

) : RealmObject()