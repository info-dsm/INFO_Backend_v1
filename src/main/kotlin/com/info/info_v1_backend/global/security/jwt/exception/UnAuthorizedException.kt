package com.info.info_v1_backend.global.security.jwt.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class UnAuthorizedException(data: String): GlobalError(ErrorCode.UN_AUTHORIZED, data) {
}