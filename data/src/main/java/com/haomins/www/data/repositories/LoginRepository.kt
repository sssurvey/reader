package com.haomins.www.data.repositories

import android.content.SharedPreferences
import com.haomins.domain.model.UserAuthResponseModel
import com.haomins.domain.repositories.LoginRepositoryContract
import com.haomins.www.data.mapper.UserAuthResponseModelMapper
import com.haomins.www.data.model.SharedPreferenceKey
import com.haomins.www.data.service.TheOldReaderService
import com.haomins.www.data.util.putValue
import com.haomins.www.data.util.removeValue
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
        private val theOldReaderService: TheOldReaderService,
        private val sharedPreferences: SharedPreferences,
        private val userAuthResponseModelMapper: UserAuthResponseModelMapper
) : LoginRepositoryContract {

    override fun login(userName: String, userPassword: String): Single<UserAuthResponseModel> {
        return theOldReaderService
                .loginUser(userName, userPassword)
                .doOnError {
                    sharedPreferences.removeValue(SharedPreferenceKey.AUTH_CODE_KEY)
                }
                .map {
                    sharedPreferences.putValue(SharedPreferenceKey.AUTH_CODE_KEY, it.auth)
                    userAuthResponseModelMapper.dataModelToDomainModel(it)
                }
    }

    override fun getSignUpUrlString(): String {
        return TheOldReaderService.SIGN_UP_PAGE_URL
    }

    override fun getGenerateAccountUrlString(): String {
        return TheOldReaderService.GENERATE_ACCOUNT_PAGE_URL
    }

}