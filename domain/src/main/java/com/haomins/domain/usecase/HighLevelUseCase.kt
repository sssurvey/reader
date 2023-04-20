package com.haomins.domain.usecase

import kotlin.reflect.KClass

/**
 * [HighLevelUseCase] marks use case that is built by using other use cases
 *
 * A [HighLevelUseCase] is a use case that utilizes other use case in the project, this use case add
 * more feature by compose other use cases. This annotation should only be applied to classes in the
 * [com.haomins.domain.usecase] package.
 *
 * @param uses an array of all the use case classes used to build this high level use case
 */
@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.CLASS)
@MustBeDocumented
annotation class HighLevelUseCase(
    val uses: Array<KClass<*>>
)
