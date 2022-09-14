package com.info.info_v1_backend.global.security.jwt.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class InvalidTokenException(data: String): GlobalError(ErrorCode.INVALID_TOKEN, data) {
}