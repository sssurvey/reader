package com.haomins.www.model

import com.haomins.www.model.model.responses.article.*
import com.haomins.www.model.model.responses.subscription.AddSourceResponseModel
import com.haomins.www.model.model.responses.subscription.SubscriptionSourceListResponseModel
import com.haomins.www.model.model.responses.user.UserAuthResponseModel
import com.haomins.www.model.service.TheOldReaderService
import io.reactivex.Single
import java.util.concurrent.TimeUnit

class MockTheOldReaderService(
        private val testSchedulingStrategy: TestSchedulingStrategy
) : TheOldReaderService {

    override fun loginUser(
            userEmail: String,
            userPassword: String,
            output: String,
            service: String,
            accountType: String,
            client: String
    ): Single<UserAuthResponseModel> {
        TODO("Not yet implemented")
    }

    override fun loadSubscriptionSourceList(
            headerAuthValue: String,
            output: String
    ): Single<SubscriptionSourceListResponseModel> {
        TODO("Not yet implemented")
    }

    override fun loadArticleListByFeed(
            headerAuthValue: String,
            feedId: String,
            articleAmount: String,
            continueLoad: String,
            output: String
    ): Single<ItemRefListResponseModel> {

        fun mockReturn(): ItemRefListResponseModel {
            return ItemRefListResponseModel(
                    itemRefs = mutableListOf<RefItemModel>().also {
                        for (index in 0..9) {
                            it.add(
                                    RefItemModel(
                                            "test_id",
                                            mutableListOf("100$it", "100$it", "100$it"),
                                            "100100100"
                                    )
                            )
                        }
                    },
                    continuation = "test_continuation"
            )
        }

        return Single
                .timer(1000, TimeUnit.MILLISECONDS, testSchedulingStrategy.subscribeOnScheduler)
                .flatMap { Single.fromCallable(::mockReturn) }
    }

    override fun loadAllArticles(
            headerAuthValue: String,
            articleAmount: String,
            continueLoad: String,
            allItemQuery: String,
            output: String
    ): Single<ItemRefListResponseModel> {

        fun mockReturn(): ItemRefListResponseModel {
            return ItemRefListResponseModel(
                    itemRefs = mutableListOf<RefItemModel>().also {
                        for (index in 0..9) {
                            it.add(
                                    RefItemModel(
                                            id = "test_id_$index",
                                            directStreamIds = mutableListOf("100$it", "100$it", "100$it"),
                                            timestampUsec = "100100100"
                                    )
                            )
                        }
                    },
                    continuation = "test_continuation"
            )
        }

        return Single
                .timer(1000, TimeUnit.MILLISECONDS, testSchedulingStrategy.subscribeOnScheduler)
                .flatMap { Single.fromCallable(::mockReturn) }
    }

    override fun loadArticleDetailsByRefId(
            headerAuthValue: String,
            refItemId: String,
            output: String
    ): Single<ArticleResponseModel> {

        fun mockReturn(): ArticleResponseModel {
            return ArticleResponseModel(
                    direction = "test_direct",
                    id = "test_id",
                    title = "test_title",
                    description = "test_description",
                    updated = 0,
                    items = mutableListOf<ArticleItemModel>().also {
                        for (index in 0..9) {
                            it.add(
                                    ArticleItemModel(
                                            crawlTimeMsec = "100100100",
                                            timestampUsec = "100100100",
                                            id = "test_id_$index",
                                            categories = mutableListOf("cate_1", "cate_2", "cate_3"),
                                            title = "test_title",
                                            summary = Summary("test_direct", "test_content"),
                                            publishedMillisecond = 1,
                                            updatedMillisecond = 1,
                                            author = "Tester Test"
                                    )
                            )
                        }
                    },
                    likingUsersCount = 1
            )
        }

        return Single
                .timer(1000, TimeUnit.MILLISECONDS, testSchedulingStrategy.subscribeOnScheduler)
                .flatMap { Single.fromCallable(::mockReturn) }
    }

    override fun addSubscription(
            headerAuthValue: String,
            quickAddSubscription: String,
            output: String
    ): Single<AddSourceResponseModel> {
        TODO("Not yet implemented")
    }

}