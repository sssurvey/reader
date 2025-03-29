package com.haomins.reader.viewModels

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.haomins.domain.usecase.logging.GetLogFilesThenSendEmail
import com.haomins.model.LogReport
import com.haomins.reader.utils.ui.DarkModeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableSingleObserver
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val darkModeManager: DarkModeManager,
    private val getLogFilesThenSendEmail: GetLogFilesThenSendEmail,
    private val application: Application
) : ViewModel() {

    companion object {
        private const val TAG = "SettingsViewModel"
        private const val FILE_PROVIDER_AUTH = "com.haomins.fileprovider"
    }

    fun enableDarkMode() {
        darkModeManager.enableDarkMode()
    }

    fun disableDarkMode() {
        darkModeManager.disableDarkMode()
    }

    fun isDarkModeEnabled(): Boolean {
        return darkModeManager.checkIsCurrentDarkModeEnabled()
    }

    fun getLogFileThenDo(action: (email: String, fileUri: Uri) -> Unit) {
        getLogFilesThenSendEmail.execute(
            object : DisposableSingleObserver<LogReport>() {
                override fun onSuccess(t: LogReport) {
                    Log.d(TAG, "getLogFilesThenSendEmail :: onSuccess")
                    action(
                        t.email,
                        FileProvider.getUriForFile(
                            application,
                            FILE_PROVIDER_AUTH,
                            t.file,
                        )
                    )
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "${e.printStackTrace()}")
                }

            }
        )
    }

}