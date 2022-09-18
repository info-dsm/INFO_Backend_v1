package com.info.info_v1_backend.domain.auth.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class IsNotStudentException(data: String): GlobalError(ErrorCode.IS_NOT_STUDENT, data) {
}