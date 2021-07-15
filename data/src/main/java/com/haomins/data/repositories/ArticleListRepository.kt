package com.haomins.data.repositories

import android.content.SharedPreferences
import android.util.Log
import com.haomins.data.mapper.entitymapper.ArticleEntityMapper
import com.haomins.data.model.SharedPreferenceKey
import com.haomins.data.model.responses.article.ArticleResponseModel
import com.haomins.data.model.responses.article.ItemRefListResponseModel
import com.haomins.data.service.RoomService
import com.haomins.data.service.TheOldReaderService
import com.haomins.data.util.getString
import com.haomins.domain.model.entities.ArticleEntity
import com.haomins.domain.repositories.ArticleListRepositoryContract
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleListRepository @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val roomService: RoomService,
    private val sharedPreferences: SharedPreferences,
    private val articleEntityMapper: ArticleEntityMapper
) : ArticleListRepositoryContract {

    companion object {
        const val TAG = "ArticleListRepository"
    }

    private var continueId = ""
    private val headerAuthValue by lazy {
        (TheOldReaderService.AUTH_HEADER_VALUE_PREFIX
                + sharedPreferences.getString(SharedPreferenceKey.AUTH_CODE_KEY))
    }

    override fun loadAllArticleItemRefs(): Observable<List<ArticleEntity>> {
        return theOldReaderService
            .loadAllArticles(headerAuthValue = headerAuthValue)
            .doOnError(::onLoadError)
            .flatMapObservable { loadIndividualArticleInformation(it) }
            .onErrorReturn { roomService.articleDao().getAll() }
//            .doOnNext { Log.d(TAG, "roomService.articleDao().getAll().toObservable() invoked") }
            .flatMap { roomService.articleDao().getAll().toObservable() }
            .flatMap {
                Observable.just(
                    it.map {
                        articleEntityMapper.dataModelToDomainModel(it)
                    }
                )
            }
    }

    override fun loadArticleItemRefs(feedId: String): Observable<List<ArticleEntity>> {
        return theOldReaderService
            .loadArticleListByFeed(headerAuthValue = headerAuthValue, feedId = feedId)
            .doOnError(::onLoadError)
            .flatMapObservable { loadIndividualArticleInformation(it) }
            .onErrorReturn { roomService.articleDao().selectAllArticleByFeedId(feedId) }
            .flatMap { roomService.articleDao().selectAllArticleByFeedId(feedId) }
            .flatMap {
                Observable.just(
                    it.map {
                        articleEntityMapper.dataModelToDomainModel(it)
                    }
                )
            }
    }

    override fun continueLoadAllArticleItemRefs(): Observable<Unit> {
        return theOldReaderService
            .loadAllArticles(headerAuthValue = headerAuthValue, continueLoad = continueId)
            .doOnError(::onLoadError)
            .doOnSuccess { continueId = it.continuation }
            .flatMapObservable { loadIndividualArticleInformation(it) }
    }

    override fun continueLoadArticleItemRefs(feedId: String): Observable<Unit> {
        return theOldReaderService
            .loadArticleListByFeed(
                headerAuthValue = headerAuthValue,
                feedId = feedId,
                continueLoad = continueId
            )
            .doOnError(::onLoadError)
            .doOnSuccess { continueId = it.continuation }
            .flatMapObservable { loadIndividualArticleInformation(it) }
    }

    private fun loadIndividualArticleInformation(itemRefListResponseModel: ItemRefListResponseModel)
            : Observable<Unit> {
        return Observable
            .fromIterable(itemRefListResponseModel.itemRefs)
            .flatMapSingle {
                theOldReaderService.loadArticleDetailsByRefId(
                    headerAuthValue = headerAuthValue,
                    refItemId = it.id
                )
            }
            .flatMapSingle { saveIndividualArticleToDatabase(it) }
    }

    private fun saveIndividualArticleToDatabase(articleResponseModel: ArticleResponseModel)
            : Single<Unit> {
        return Single.fromCallable {
            roomService.articleDao().insert(
                com.haomins.data.model.entities.ArticleEntity(
                    itemId = articleResponseModel.items.first().id,
                    itemTitle = articleResponseModel.items.first().title,
                    itemUpdatedMillisecond = articleResponseModel.items.first().updatedMillisecond,
                    itemPublishedMillisecond = articleResponseModel.items.first().publishedMillisecond,
                    author = articleResponseModel.items.first().author,
                    content = articleResponseModel.items.first().summary.content,
                    feedId = articleResponseModel.id
                )
            )
        }
    }

    private fun onLoadError(e: Throwable) {
        Log.d(TAG, "onError :: ${e.printStackTrace()}")
    }

}