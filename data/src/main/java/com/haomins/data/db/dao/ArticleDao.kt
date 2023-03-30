package com.haomins.data.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface ArticleDao {

    @Query("DELETE FROM article_entity")
    fun clearTable()

    @Query("SELECT * FROM article_entity WHERE item_id == :itemId")
    fun selectArticleByItemId(itemId: String): Single<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articleEntities: List<ArticleEntity>): Completable

    @Query("SELECT * FROM article_entity ORDER BY published DESC")
    fun getAll(): PagingSource<Int, ArticleEntity>

    @Query("SELECT * FROM article_entity WHERE feed_id == :feedId ORDER BY published DESC")
    fun getAllFromFeed(feedId: String): PagingSource<Int, ArticleEntity>

}