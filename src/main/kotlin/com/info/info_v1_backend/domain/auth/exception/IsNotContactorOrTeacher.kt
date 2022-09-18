package com.info.info_v1_backend.domain.auth.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class IsNotContactorOrTeacher(data: String): GlobalError(ErrorCode.IS_NOT_CONTACTOR_OR_TEACHER, data) {
}