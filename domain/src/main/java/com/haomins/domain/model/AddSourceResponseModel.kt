package com.haomins.domain.model

data class AddSourceResponseModel(
    // 1 - Success, 0 - Fail
    val result: Int,
    // null if success, else non-null
    val error: String?
)