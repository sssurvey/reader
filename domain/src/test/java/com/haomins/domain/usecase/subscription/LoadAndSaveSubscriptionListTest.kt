package com.haomins.domain.usecase.subscription

import com.haomins.domain.MockGenerator
import com.haomins.domain.TestSchedulers
import com.haomins.domain.usecase.subscription.local.LoadSubscriptionListFromLocal
import com.haomins.domain.usecase.subscription.local.SaveSubscriptionListToLocal
import com.haomins.domain.usecase.subscription.remote.LoadSubscriptionListFromRemote
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class LoadAndSaveSubscriptionListTest {

    @Mock
    lateinit var loadSubscriptionListFromRemote: LoadSubscriptionListFromRemote

    @Mock
    lateinit var saveSubscriptionListToLocal: SaveSubscriptionListToLocal

    @Mock
    lateinit var loadSubscriptionListFromLocal: LoadSubscriptionListFromLocal

    private lateinit var loadAndSaveSubscriptionList: LoadAndSaveSubscriptionList

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        loadAndSaveSubscriptionList = LoadAndSaveSubscriptionList(
            loadSubscriptionListFromRemote,
            saveSubscriptionListToLocal,
            loadSubscriptionListFromLocal,
            TestSchedulers.executionSchedulerTrampolineScheduler(),
            TestSchedulers.postExecutionScheduler()
        )
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `loadAndSaveSubscriptionList initialized successfully`() {
        assertTrue(this::loadAndSaveSubscriptionList.isInitialized)
    }

    @Test
    fun `are correct instance called when buildUseCaseSuccessful`() {
        val mockSubscriptionItemList = MockGenerator.generateSubscriptionListResponseModel().subscriptions
        val mockSubscriptionEntityList = loadAndSaveSubscriptionList.convertSubscriptionItemModelToEntityForTesting(mockSubscriptionItemList)
        `when`(
            loadSubscriptionListFromRemote.buildUseCaseSingle(Unit)
        )
            .then {
                Single.fromCallable { mockSubscriptionItemList }
            }
        `when`(
            saveSubscriptionListToLocal.buildUseCaseCompletable(
                SaveSubscriptionListToLocal.forSaveSubscriptionList(
                    mockSubscriptionEntityList
                )
            )
        )
            .then { Completable.complete() }
        `when`(loadSubscriptionListFromLocal.buildUseCaseSingle(Unit))
            .then {
                Single.fromCallable { mockSubscriptionEntityList }
            }

        val testObserver = loadAndSaveSubscriptionList.buildUseCaseSingle(Unit).test()

        testObserver.assertComplete()

        verify(loadSubscriptionListFromRemote, times(1)).buildUseCaseSingle(Unit)
        verify(saveSubscriptionListToLocal, times(1)).buildUseCaseCompletable(
            SaveSubscriptionListToLocal.forSaveSubscriptionList(mockSubscriptionEntityList)
        )
        verify(loadSubscriptionListFromLocal, times(1)).buildUseCaseSingle(Unit)
    }

    @Test
    fun `are correct instance called when buildUseCase OnError`() {

        val mockSubscriptionItemList = MockGenerator.generateSubscriptionListResponseModel().subscriptions
        val mockSubscriptionEntityList = loadAndSaveSubscriptionList.convertSubscriptionItemModelToEntityForTesting(mockSubscriptionItemList)

        `when`(
            loadSubscriptionListFromRemote.buildUseCaseSingle(Unit)
        )
            .then {
                Single.fromCallable {
                    throw Exception("")
                }
            }

        `when`(
            saveSubscriptionListToLocal.buildUseCaseCompletable(
                SaveSubscriptionListToLocal.forSaveSubscriptionList(
                    mockSubscriptionEntityList
                )
            )
        )
            .then { Completable.error(Throwable("")) }

        `when`(loadSubscriptionListFromLocal.buildUseCaseSingle(Unit))
            .then {
                Single.fromCallable { mockSubscriptionEntityList }
            }

        val testObserver = loadAndSaveSubscriptionList.buildUseCaseSingle(Unit).test()

        testObserver.assertComplete()

        verify(loadSubscriptionListFromRemote, times(1)).buildUseCaseSingle(Unit)
        verify(saveSubscriptionListToLocal, times(0)).buildUseCaseCompletable(
            SaveSubscriptionListToLocal.forSaveSubscriptionList(mockSubscriptionEntityList)
        )
        // Normal for loadSubscriptionListFromLocal#buildUseCaseSingle to be called twice,
        // https://stackoverflow.com/questions/56516502/rxjava2-andthen-called-even-though-first-completable-emitted-error
        verify(loadSubscriptionListFromLocal, times(2)).buildUseCaseSingle(Unit)
    }

    @Test
    fun `is convertSubscriptionItemModelToEntityForTesting correct`() {

        val mockSubscriptionItemList = MockGenerator.generateSubscriptionListResponseModel().subscriptions
        val mockSubscriptionEntityList = loadAndSaveSubscriptionList.convertSubscriptionItemModelToEntityForTesting(mockSubscriptionItemList)

        for (i in 0 until mockSubscriptionItemList.size) {
            assertTrue(mockSubscriptionItemList[i].id == mockSubscriptionEntityList[i].id)
        }
    }
}