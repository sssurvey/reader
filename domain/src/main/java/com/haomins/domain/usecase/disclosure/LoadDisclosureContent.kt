package com.haomins.domain.usecase.disclosure

import com.haomins.model.DisclosureInfo
import com.haomins.domain.repositories.ContactInfoLocalRepository
import com.haomins.domain.repositories.DisclosureLocalRepository
import com.haomins.domain.scheduler.ExecutionScheduler
import com.haomins.domain.scheduler.PostExecutionScheduler
import com.haomins.domain.usecase.SingleUseCase
import io.reactivex.Single
import javax.inject.Inject

class LoadDisclosureContent @Inject constructor(
    private val disclosureLocalRepository: DisclosureLocalRepository,
    private val contactInfoLocalRepository: ContactInfoLocalRepository,
    executionScheduler: ExecutionScheduler,
    postExecutionScheduler: PostExecutionScheduler
) : SingleUseCase<Unit, DisclosureInfo>(
    executionScheduler,
    postExecutionScheduler
) {

    override fun buildUseCaseSingle(params: Unit?): Single<DisclosureInfo> {
        return disclosureLocalRepository
            .loadDisclosureContent()
            .flatMap {
                Single.just(
                    DisclosureInfo(
                        disclosureContent = it,
                        contactEmail = contactInfoLocalRepository.getFeedbackEmail(),
                        website = contactInfoLocalRepository.getTheOldReaderSite()
                    )
                )
            }
    }
}