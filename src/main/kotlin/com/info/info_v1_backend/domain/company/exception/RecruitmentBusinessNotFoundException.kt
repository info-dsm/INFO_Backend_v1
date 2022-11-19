package com.info.info_v1_backend.domain.company.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class RecruitmentBusinessNotFoundException(data: String): GlobalError(ErrorCode.RECRUITMENT_BUSINESS_NOT_FOUND, data) {
}