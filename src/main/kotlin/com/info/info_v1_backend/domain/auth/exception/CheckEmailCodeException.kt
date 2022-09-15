package com.info.info_v1_backend.domain.auth.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class CheckEmailCodeException(data: String): GlobalError(ErrorCode.CHECK_EMAIL_CODE_ERROR, data) {
}