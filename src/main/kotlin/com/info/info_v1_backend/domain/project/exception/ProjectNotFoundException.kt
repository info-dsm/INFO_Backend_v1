package com.info.info_v1_backend.domain.project.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class ProjectNotFoundException(data: String): GlobalError(ErrorCode.PROJECT_NOT_FOUND, data) {
}