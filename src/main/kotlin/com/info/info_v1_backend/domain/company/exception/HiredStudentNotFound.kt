package com.info.info_v1_backend.domain.company.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class HiredStudentNotFound(data: String): GlobalError(ErrorCode.HIRED_STUDENT_NOT_FOUND, data) {
}