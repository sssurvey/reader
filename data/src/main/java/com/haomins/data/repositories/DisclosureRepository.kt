package com.haomins.data.repositories

import android.app.Application
import android.util.Log
import com.haomins.domain.repositories.DisclosureRepositoryContract
import io.reactivex.Single
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class DisclosureRepository @Inject constructor(
    private val application: Application
) : DisclosureRepositoryContract {

    companion object {
        private const val TAG = "DisclosureRepository"
        private const val ASSET_LOADING_ERROR = "Failed to load disclosure"
        private const val DISCLOSURE_FILENAME = "news_disclosures.txt"
    }

    override fun loadDisclosureContent(): Single<String> {
        return Single.fromCallable { loadAsset() }
    }

    private fun loadAsset(): String {

        val bufferedReader: BufferedReader
        var result: String

        try {

            Log.d(TAG, "loadAsset :: try loading asset -> $DISCLOSURE_FILENAME")

            bufferedReader =
                BufferedReader(InputStreamReader(application.assets.open(DISCLOSURE_FILENAME)))

            var currentLine: String? = bufferedReader.readLine()
            val stringBuilder = StringBuilder()

            while (currentLine != null) {
                if (!currentLine.isNullOrBlank()) {
                    stringBuilder.append(currentLine)
                }
                currentLine = bufferedReader.readLine()
            }

            result = stringBuilder.toString()

        } catch (e: Exception) {
            Log.e(TAG, "loadAsset :: ${e.printStackTrace()}")
            result = ASSET_LOADING_ERROR
        } finally {
            Log.e(TAG, "loadAsset :: finally")
        }

        return result
    }


}