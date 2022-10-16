package com.info.info_v1_backend.domain.company.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class StudentAlreadyWrittenCommentException(data: String): GlobalError(ErrorCode.STUDENT_ALREADY_WRITTEN_COMMENT, data) {
}