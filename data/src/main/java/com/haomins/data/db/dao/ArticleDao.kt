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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg articleEntity: ArticleEntity)

    @Query("DELETE FROM article_entity")
    fun clearTable()

    @Query("SELECT * FROM article_entity")
    fun getAll(): Single<List<ArticleEntity>>

    @Query("SELECT * FROM article_entity WHERE feed_id == :feedId")
    fun selectAllArticleByFeedId(feedId: String): Single<List<ArticleEntity>>

    @Query("SELECT * FROM article_entity WHERE item_id == :itemId")
    fun selectArticleByItemId(itemId: String): Single<ArticleEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertV2(articleEntities: List<ArticleEntity>): Completable

    @Query("DELETE FROM article_entity")
    fun clearTableV2(): Completable

    @Query("SELECT * FROM article_entity")
    fun getAllV2(): PagingSource<Int, ArticleEntity>

    @Query("SELECT * FROM article_entity WHERE feed_id == :feedId")
    fun getAllV2WithFeed(feedId: String): PagingSource<Int, ArticleEntity>
}