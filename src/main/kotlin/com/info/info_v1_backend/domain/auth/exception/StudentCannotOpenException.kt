package com.info.info_v1_backend.domain.auth.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class StudentCannotOpenException(data: String): GlobalError(ErrorCode.STUDENT_CANNOT_OPEN, data) {
}