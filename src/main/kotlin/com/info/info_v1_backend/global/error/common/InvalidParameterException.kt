package com.info.info_v1_backend.global.error.common

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class InvalidParameterException(data: String): GlobalError(ErrorCode.INVALID_PARAMETER, data) {
}