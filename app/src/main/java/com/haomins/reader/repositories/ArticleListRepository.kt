package com.haomins.reader.repositories

import com.haomins.reader.TheOldReaderService
import com.haomins.reader.data.AppDatabase
import javax.inject.Inject

class ArticleListRepository @Inject constructor(
    private val theOldReaderService: TheOldReaderService,
    private val appDatabase: AppDatabase
) {
    
}