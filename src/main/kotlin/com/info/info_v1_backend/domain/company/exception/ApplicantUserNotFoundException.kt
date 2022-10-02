package com.info.info_v1_backend.domain.company.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class ApplicantUserNotFoundException(data: String): GlobalError(ErrorCode.APPLICANT_USER_NOT_FOUND, data) {
}