package com.haomins.reader.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haomins.reader.data.entities.ArticleEntity
import io.reactivex.Observable
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
    fun selectAllArticleByFeedId(feedId: String): Observable<List<ArticleEntity>>

}