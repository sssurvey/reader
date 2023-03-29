package com.haomins.data.datastore.paging

import androidx.paging.*
import com.haomins.data.datastore.local.ArticleListLocalDataStore
import com.haomins.data.datastore.remote.ArticleListRemoteDataStore
import com.haomins.domain.common.HtmlUtil
import com.haomins.domain.common.ModelToEntityMapper
import com.haomins.model.remote.article.Alternate
import com.haomins.model.remote.article.ArticleItemModel
import com.haomins.model.remote.article.ArticleResponseModel
import com.haomins.model.remote.article.Summary
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class ArticleListRemoteMediatorTest {

    @Mock
    lateinit var articleListRemoteDataStore: ArticleListRemoteDataStore

    @Mock
    lateinit var articleListLocalDataStore: ArticleListLocalDataStore

    private lateinit var articleListRemoteMediator: ArticleListRemoteMediator

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        articleListRemoteMediator = ArticleListRemoteMediator(
            articleListRemoteDataStore,
            articleListLocalDataStore,
            ModelToEntityMapper(HtmlUtil())
        )
    }

    @Test
    fun `articleListRemoteMediator should initialize correctly`() {
        assertTrue(this::articleListRemoteMediator.isInitialized)
    }

    @Test
    fun `loadSingle refresh for all feed not the end of page`() {

        `when`(articleListLocalDataStore.saveAllArticles(any())).thenReturn(Completable.complete())
        `when`(articleListRemoteDataStore.loadAllArticleItemsFromRemote(""))
            .thenReturn(
                Single.just(
                    "test_continue_id" to listOf(
                        ArticleResponseModel(
                            "rtl",
                            "test_id",
                            "test title",
                            "just a test",
                            0,
                            listOf(
                                ArticleItemModel(
                                    "",
                                    "",
                                    "",
                                    listOf(),
                                    "",
                                    Summary("", ""),
                                    0,
                                    0,
                                    ""
                                )
                            ),
                            0,
                            Alternate("", "")
                        )
                    )
                )
            )

        val testObserver = articleListRemoteMediator.loadSingle(
            LoadType.REFRESH,
            state = PagingState(
                arrayListOf(),
                null,
                PagingConfig(10),
                10
            )
        ).test()

        verify(articleListRemoteDataStore, times(1))
            .loadAllArticleItemsFromRemote("")
        verify(articleListLocalDataStore, times(1)).saveAllArticles(any())

        testObserver.assertComplete()
        assertTrue(testObserver.values().first() is RemoteMediator.MediatorResult.Success)
        assertTrue(!(testObserver.values().first() as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `loadSingle refresh for all feed reached the end of page`() {

        `when`(articleListLocalDataStore.saveAllArticles(any())).thenReturn(Completable.complete())
        `when`(articleListRemoteDataStore.loadAllArticleItemsFromRemote(""))
            .thenReturn(
                Single.just(
                    "" to listOf(
                        ArticleResponseModel(
                            "rtl",
                            "test_id",
                            "test title",
                            "just a test",
                            0,
                            listOf(
                                ArticleItemModel(
                                    "",
                                    "",
                                    "",
                                    listOf(),
                                    "",
                                    Summary("", ""),
                                    0,
                                    0,
                                    ""
                                )
                            ),
                            0,
                            Alternate("", "")
                        )
                    )
                )
            )

        val testObserver = articleListRemoteMediator.loadSingle(
            LoadType.REFRESH,
            state = PagingState(
                arrayListOf(),
                null,
                PagingConfig(10),
                10
            )
        ).test()

        verify(articleListRemoteDataStore, times(1)).loadAllArticleItemsFromRemote("")
        verify(articleListLocalDataStore, times(1)).saveAllArticles(any())

        testObserver.assertComplete()
        assertTrue(testObserver.values().first() is RemoteMediator.MediatorResult.Success)
        assertTrue((testObserver.values().first() as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `loadSingle refresh for specific feed not the end of page`() {
        articleListRemoteMediator.feedId = "test_feed_id"
        `when`(articleListLocalDataStore.saveAllArticles(any())).thenReturn(Completable.complete())
        `when`(articleListRemoteDataStore.loadAllArticleItemsFromRemoteWithFeed("test_feed_id", ""))
            .thenReturn(
                Single.just(
                    "test_continue_id" to listOf(
                        ArticleResponseModel(
                            "rtl",
                            "test_id",
                            "test title",
                            "just a test",
                            0,
                            listOf(
                                ArticleItemModel(
                                    "",
                                    "",
                                    "",
                                    listOf(),
                                    "",
                                    Summary("", ""),
                                    0,
                                    0,
                                    ""
                                )
                            ),
                            0,
                            Alternate("", "")
                        )
                    )
                )
            )

        val testObserver = articleListRemoteMediator.loadSingle(
            LoadType.REFRESH,
            state = PagingState(
                arrayListOf(),
                null,
                PagingConfig(10),
                10
            )
        ).test()

        verify(
            articleListRemoteDataStore,
            times(1)
        ).loadAllArticleItemsFromRemoteWithFeed("test_feed_id", "")
        verify(articleListLocalDataStore, times(1)).saveAllArticles(any())

        testObserver.assertComplete()
        assertTrue(testObserver.values().first() is RemoteMediator.MediatorResult.Success)
        assertTrue(!(testObserver.values().first() as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `loadSingle refresh for specific feed reached the end of page`() {
        articleListRemoteMediator.feedId = "test_feed_id"
        `when`(articleListLocalDataStore.saveAllArticles(any())).thenReturn(Completable.complete())
        `when`(articleListRemoteDataStore.loadAllArticleItemsFromRemoteWithFeed("test_feed_id", ""))
            .thenReturn(
                Single.just(
                    "" to listOf(
                        ArticleResponseModel(
                            "rtl",
                            "test_id",
                            "test title",
                            "just a test",
                            0,
                            listOf(
                                ArticleItemModel(
                                    "",
                                    "",
                                    "",
                                    listOf(),
                                    "",
                                    Summary("", ""),
                                    0,
                                    0,
                                    ""
                                )
                            ),
                            0,
                            Alternate("", "")
                        )
                    )
                )
            )

        val testObserver = articleListRemoteMediator.loadSingle(
            LoadType.REFRESH,
            state = PagingState(
                arrayListOf(),
                null,
                PagingConfig(10),
                10
            )
        ).test()

        verify(
            articleListRemoteDataStore,
            times(1)
        ).loadAllArticleItemsFromRemoteWithFeed("test_feed_id", "")
        verify(articleListLocalDataStore, times(1)).saveAllArticles(any())

        testObserver.assertComplete()
        assertTrue(testObserver.values().first() is RemoteMediator.MediatorResult.Success)
        assertTrue((testObserver.values().first() as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `prepend should return success right away, since we don't want to have item prepend`() {
        val testObserver = articleListRemoteMediator.loadSingle(
            LoadType.PREPEND,
            state = PagingState(
                arrayListOf(),
                null,
                PagingConfig(10),
                10
            )
        ).test()
        testObserver.assertComplete()
        assertTrue(testObserver.values().first() is RemoteMediator.MediatorResult.Success)
        assertTrue((testObserver.values().first() as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `append should use the last continue ID as continueID for new data`() {
        articleListRemoteMediator.feedId = ""
        `when`(articleListLocalDataStore.saveAllArticles(any())).thenReturn(Completable.complete())
        `when`(articleListRemoteDataStore.loadAllArticleItemsFromRemote(""))
            .thenReturn(
                Single.just(
                    "test_continue_id" to listOf(
                        ArticleResponseModel(
                            "rtl",
                            "test_id",
                            "test title",
                            "just a test",
                            0,
                            listOf(
                                ArticleItemModel(
                                    "",
                                    "",
                                    "",
                                    listOf(),
                                    "",
                                    Summary("", ""),
                                    0,
                                    0,
                                    ""
                                )
                            ),
                            0,
                            Alternate("", "")
                        )
                    )
                )
            )

        val testObserver = articleListRemoteMediator.loadSingle(
            LoadType.REFRESH,
            state = PagingState(
                arrayListOf(),
                null,
                PagingConfig(10),
                10
            )
        ).test()

        testObserver.assertComplete()
        assertTrue(testObserver.values().first() is RemoteMediator.MediatorResult.Success)
        assertTrue(!(testObserver.values().first() as RemoteMediator.MediatorResult.Success).endOfPaginationReached)

        `when`(articleListRemoteDataStore.loadAllArticleItemsFromRemote("test_continue_id"))
            .thenReturn(
                Single.just(
                    "test_continue_id_2" to listOf(
                        ArticleResponseModel(
                            "rtl",
                            "test_id",
                            "test title",
                            "just a test",
                            0,
                            listOf(
                                ArticleItemModel(
                                    "",
                                    "",
                                    "",
                                    listOf(),
                                    "",
                                    Summary("", ""),
                                    0,
                                    0,
                                    ""
                                )
                            ),
                            0,
                            Alternate("", "")
                        )
                    )
                )
            )

        val testObserverAppend = articleListRemoteMediator.loadSingle(
            LoadType.APPEND,
            state = PagingState(
                arrayListOf(),
                null,
                PagingConfig(10),
                10
            )
        ).test()

        verify(articleListRemoteDataStore, times(1))
            .loadAllArticleItemsFromRemote("test_continue_id") // previous id
        verify(articleListLocalDataStore, times(2)).saveAllArticles(any())

        testObserver.assertComplete()
        assertTrue(testObserver.values().first() is RemoteMediator.MediatorResult.Success)
        assertTrue(!(testObserver.values().first() as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `loadSingle IO error should invoke MediatorError`() {
        articleListRemoteMediator.feedId = "test_feed_id"
        `when`(articleListLocalDataStore.saveAllArticles(any())).thenReturn(Completable.complete())
        `when`(articleListRemoteDataStore.loadAllArticleItemsFromRemoteWithFeed("test_feed_id", ""))
            .thenReturn(
                Single.error(IOException())
            )

        val testObserver = articleListRemoteMediator.loadSingle(
            LoadType.REFRESH,
            state = PagingState(
                arrayListOf(),
                null,
                PagingConfig(10),
                10
            )
        ).test()

        testObserver.assertComplete()
        assertTrue(testObserver.values().first() is RemoteMediator.MediatorResult.Error)
    }

    @Test
    fun `loadSingle HTTP error should invoke MediatorError`() {
        val mockHttpException = mock<HttpException>()
        articleListRemoteMediator.feedId = "test_feed_id"
        `when`(articleListLocalDataStore.saveAllArticles(any())).thenReturn(Completable.complete())
        `when`(articleListRemoteDataStore.loadAllArticleItemsFromRemoteWithFeed("test_feed_id", ""))
            .thenReturn(
                Single.error(mockHttpException)
            )

        val testObserver = articleListRemoteMediator.loadSingle(
            LoadType.REFRESH,
            state = PagingState(
                arrayListOf(),
                null,
                PagingConfig(10),
                10
            )
        ).test()

        testObserver.assertComplete()
        assertTrue(testObserver.values().first() is RemoteMediator.MediatorResult.Error)
    }

    @Test
    fun `loadSingle OTHER error should invoke Single onError`() {
        val testException = Exception("test")
        articleListRemoteMediator.feedId = "test_feed_id"
        `when`(articleListLocalDataStore.saveAllArticles(any())).thenReturn(Completable.complete())
        `when`(articleListRemoteDataStore.loadAllArticleItemsFromRemoteWithFeed("test_feed_id", ""))
            .thenReturn(
                Single.error(testException)
            )

        val testObserver = articleListRemoteMediator.loadSingle(
            LoadType.REFRESH,
            state = PagingState(
                arrayListOf(),
                null,
                PagingConfig(10),
                10
            )
        ).test()

        testObserver.assertError(testException)
    }
}