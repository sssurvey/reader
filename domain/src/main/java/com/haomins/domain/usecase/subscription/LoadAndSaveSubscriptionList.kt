package com.haomins.domain.usecase.subscription

import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import com.haomins.domain.usecase.subscription.local.LoadSubscriptionListFromLocal
import com.haomins.domain.usecase.subscription.local.SaveSubscriptionListToLocal
import com.haomins.domain.usecase.subscription.remote.LoadSubscriptionListFromRemote
import com.haomins.model.entity.SubscriptionEntity
import com.haomins.model.remote.subscription.SubscriptionItemModel
import io.reactivex.Single
import org.jetbrains.annotations.TestOnly
import javax.inject.Inject

class LoadAndSaveSubscriptionList @Inject constructor(
    private val loadSubscriptionListFromRemote: LoadSubscriptionListFromRemote,
    private val saveSubscriptionListToLocal: SaveSubscriptionListToLocal,
    private val loadSubscriptionListFromLocal: LoadSubscriptionListFromLocal,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<Unit, List<SubscriptionEntity>>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Unit?): Single<List<SubscriptionEntity>> {
        return loadSubscriptionListFromRemote
            .buildUseCaseSingle(Unit)
            .map {
                it.convertSubscriptionItemModelToEntity()
            }
            .flatMapCompletable {
                saveSubscriptionListToLocal
                    .buildUseCaseCompletable(
                        SaveSubscriptionListToLocal.forSaveSubscriptionList(it)
                    )
            }
            .andThen(
                loadSubscriptionListFromLocal.buildUseCaseSingle(Unit)
            )
            .onErrorResumeNext {
                loadSubscriptionListFromLocal.buildUseCaseSingle(Unit)
            }
    }

    private fun List<SubscriptionItemModel>.convertSubscriptionItemModelToEntity()
            : List<SubscriptionEntity> {
        return map {
            SubscriptionEntity(
                id = it.id,
                title = it.title,
                sortId = it.sortId,
                firstItemMilSec = it.firstItemMilSec,
                url = it.url,
                htmlUrl = it.htmlUrl,
                /*
                 * For some reason this is missing from BE, we need to manually
                 *  append it here.
                 */
                iconUrl = DEFAULT_HTTPS_PROTOCOL + it.iconUrl
            )
        }
    }

    @TestOnly
    fun convertSubscriptionItemModelToEntityForTesting(subscriptions: ArrayList<SubscriptionItemModel>)
            : List<SubscriptionEntity> {
        return subscriptions.convertSubscriptionItemModelToEntity()
    }

    companion object {
        private const val DEFAULT_HTTPS_PROTOCOL = "https:"
    }
}