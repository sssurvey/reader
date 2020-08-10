package com.haomins.www.core.repositories

import android.content.SharedPreferences
import com.haomins.www.core.TestSchedulingStrategy
import com.haomins.www.core.service.RoomService
import com.haomins.www.core.service.TheOldReaderService
import io.reactivex.schedulers.TestScheduler
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ArticleListRepositoryTest {

    @Mock
    lateinit var mockTheOldReaderService: TheOldReaderService

    @Mock
    lateinit var mockRoomService: RoomService

    @Mock
    lateinit var mockSharedPreferences: SharedPreferences

    private val testScheduler = TestScheduler()
    private val testSchedulingStrategy = TestSchedulingStrategy(subscribeOnScheduler = testScheduler)
    private lateinit var articleListRepository: ArticleListRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        articleListRepository = ArticleListRepository(
            mockTheOldReaderService,
            mockRoomService,
            mockSharedPreferences,
            testSchedulingStrategy
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun loadAllArticleItemRefs() {
    }

    @Test
    fun loadArticleItemRefs() {
    }

    @Test
    fun continueLoadAllArticleItemRefs() {
    }

    @Test
    fun continueLoadArticleItemRefs() {
    }

    private fun mockHelper() {
        
    }
}