package com.info.info_v1_backend.global.error.data

import com.info.info_v1_backend.global.error.ErrorCode

open class GlobalError(
    val errorCode: ErrorCode,
    val data: String
    ): RuntimeException(errorCode.message) {
}