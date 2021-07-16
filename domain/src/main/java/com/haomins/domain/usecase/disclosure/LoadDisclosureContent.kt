package com.haomins.domain.usecase.disclosure

import com.haomins.domain.model.DisclosureInfo
import com.haomins.domain.repositories.ContactInfoRepositoryContract
import com.haomins.domain.repositories.DisclosureRepositoryContract
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class LoadDisclosureContent @Inject constructor(
    private val disclosureRepositoryContract: DisclosureRepositoryContract,
    private val contactInfoRepositoryContract: ContactInfoRepositoryContract,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<Unit, DisclosureInfo>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Unit?): Single<DisclosureInfo> {
        return disclosureRepositoryContract
            .loadDisclosureContent()
            .flatMap {
                Single.just(
                    DisclosureInfo(
                        disclosureContent = it,
                        contactEmail = contactInfoRepositoryContract.getFeedbackEmail(),
                        website = contactInfoRepositoryContract.getTheOldReaderSite()
                    )
                )
            }
    }
}