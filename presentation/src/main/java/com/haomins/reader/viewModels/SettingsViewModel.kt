package com.haomins.reader.viewModels

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModel
import com.haomins.domain.usecase.logging.GetLogFiles
import com.haomins.reader.utils.DarkModeManager
import io.reactivex.observers.DisposableSingleObserver
import java.io.File
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
    private val darkModeManager: DarkModeManager,
    private val getLogFiles: GetLogFiles,
    private val application: Application
) : ViewModel() {

    companion object {
        private const val TAG = "SettingsViewModel"
        private const val FEEDBACK_EMAIL = "youngmobileachiever@gmail.com"
        private const val FILE_PROVIDER_AUTH = "com.haomins.fileprovider"
    }

    fun enableDarkMode() {
        darkModeManager.enableDarkMode()
    }

    fun getFeedbackEmail(): String {
        return FEEDBACK_EMAIL
    }

    fun disableDarkMode() {
        darkModeManager.disableDarkMode()
    }

    fun isDarkModeEnabled(): Boolean {
        return darkModeManager.checkIsCurrentDarkModeEnabled()
    }

    fun getLogFileThenDo(action: (fileUri: Uri) -> Unit) {
        getLogFiles.execute(
            object : DisposableSingleObserver<File>() {
                override fun onSuccess(t: File) {
                    Log.d(TAG, "getLogFiles :: onSuccess")
                    action.invoke(
                        FileProvider.getUriForFile(
                            application,
                            FILE_PROVIDER_AUTH,
                            t
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