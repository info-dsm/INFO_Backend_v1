package com.info.info_v1_backend.domain.project.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class NotHaveAccessProjectException(data: String): GlobalError(ErrorCode.NOT_HAVE_ACCESS_TO_THE_PROJECT, data) {
}