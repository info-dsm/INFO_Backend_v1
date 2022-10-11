package com.info.info_v1_backend.domain.company.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class AlreadyExistsException(data: String): GlobalError(ErrorCode.ALREAD_EXISTS, data) {
}