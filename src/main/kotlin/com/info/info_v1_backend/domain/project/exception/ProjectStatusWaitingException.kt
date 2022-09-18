package com.info.info_v1_backend.domain.project.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class ProjectStatusWaitingException(data: String): GlobalError(ErrorCode.PROJECT_STATUS_WAITING, data) {
}