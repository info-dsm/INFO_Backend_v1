package com.info.info_v1_backend.domain.company.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class AlreadyApproveNotice(data: String): GlobalError(ErrorCode.ALREADY_APPROVE_NOTICE, data) {
}