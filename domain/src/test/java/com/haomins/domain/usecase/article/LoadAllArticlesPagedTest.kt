package com.haomins.domain.usecase.article

import androidx.paging.PagingData
import com.haomins.domain.TestSchedulers
import com.haomins.domain.repositories.ArticleListPagerRepository
import com.haomins.model.entity.ArticleEntity
import io.reactivex.Flowable
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class LoadAllArticlesPagedTest {

    @Mock
    lateinit var articleListPagerRepository: ArticleListPagerRepository

    @Mock
    lateinit var pagingData: PagingData<ArticleEntity>

    private lateinit var loadAllArticlesPaged: LoadAllArticlesPaged

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)

        `when`(articleListPagerRepository.getArticleListStream())
            .thenReturn(Flowable.just(pagingData))
        `when`(articleListPagerRepository.getArticleListStream("test_id"))
            .thenReturn(Flowable.just(pagingData))
        loadAllArticlesPaged = LoadAllArticlesPaged(
            articleListPagerRepository,
            TestSchedulers.executionSchedulerTrampolineScheduler(),
            TestSchedulers.postExecutionSchedulerTrampolineScheduler()
        )
    }

    @Test
    fun `loadAllArticlesPaged should initialize correctly`() {
        assertTrue(this::loadAllArticlesPaged.isInitialized)
    }

    @Test
    fun `articleListPagerRepository#getArticleListStream should be called if no param`() {
        loadAllArticlesPaged.buildUseCaseFlowable(null).subscribe()
        verify(articleListPagerRepository, times(1)).getArticleListStream()
        verify(articleListPagerRepository, times(0))
            .getArticleListStream("test_id")
    }

    @Test
    fun `articleListPagerRepository#getArticleListStream(id) should be called if no param`() {
        loadAllArticlesPaged
            .buildUseCaseFlowable(
                LoadAllArticlesPaged.forLoadAllArticlesPaged("test_id")
            )
            .subscribe()
        verify(articleListPagerRepository, times(0)).getArticleListStream()
        verify(articleListPagerRepository, times(1))
            .getArticleListStream("test_id")
    }
}