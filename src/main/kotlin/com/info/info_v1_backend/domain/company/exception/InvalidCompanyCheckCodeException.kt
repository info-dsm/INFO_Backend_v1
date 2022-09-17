package com.info.info_v1_backend.domain.company.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class InvalidCompanyCheckCodeException(data: String): GlobalError(ErrorCode.INVALID_COMPANY_CHECK_CODE, data) {
}