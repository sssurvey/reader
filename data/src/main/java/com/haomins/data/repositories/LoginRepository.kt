package com.haomins.data.repositories

import android.content.SharedPreferences
import com.haomins.data.mapper.responsemapper.UserAuthResponseModelMapper
import com.haomins.data_model.SharedPreferenceKey
import com.haomins.data.service.TheOldReaderService
import com.haomins.data.util.putValue
import com.haomins.data.util.removeValue
import com.haomins.domain.model.responses.UserAuthResponseModel
import com.haomins.domain.repositories.LoginRepositoryContract
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

    override fun getForgetPasswordUrlString(): String {
        return TheOldReaderService.FORGET_PASSWORD_PAGE_URL
    }

}