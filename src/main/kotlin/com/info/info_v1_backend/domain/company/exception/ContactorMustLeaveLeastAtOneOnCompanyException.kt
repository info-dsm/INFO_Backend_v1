package com.info.info_v1_backend.domain.company.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class ContactorMustLeaveLeastAtOneOnCompanyException(data: String): GlobalError(ErrorCode.CONTACTOR_MUST_LEAVE_LEAST_AT_ONE_ON_COMPANY, data) {
}