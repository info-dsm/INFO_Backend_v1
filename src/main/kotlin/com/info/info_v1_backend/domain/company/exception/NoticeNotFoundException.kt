package com.info.info_v1_backend.domain.company.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class NoticeNotFoundException(data: String): GlobalError(ErrorCode.NOTICE_NOT_FOUND, data) {
}