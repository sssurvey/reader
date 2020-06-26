package com.haomins.www.core.data.realmentities

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmField

open class ArticleEntity(

    @PrimaryKey
    @RealmField(name = "item_id")
    var itemId: String = "",

    @RealmField(name = "feed_id")
    var feedId: String = "",

    @RealmField(name = "item_title")
    var itemTitle: String = "",

    @RealmField(name = "updated_at_time_millisecond")
    var itemUpdatedMillisecond: Long = 0L,

    @RealmField(name = "publish_at_time_millisecond")
    var itemPublishedMillisecond: Long = 0L,

    @RealmField(name = "author")
    var author: String = "",

    @RealmField(name = "html_content")
    var content: String = ""
) : RealmObject()