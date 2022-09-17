package com.info.info_v1_backend.domain.company.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class IsNotContactorCompany(data: String): GlobalError(ErrorCode.IS_NOT_CONTACTOR_COMPANY, data) {
}