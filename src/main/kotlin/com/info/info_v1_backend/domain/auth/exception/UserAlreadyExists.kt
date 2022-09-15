package com.info.info_v1_backend.domain.auth.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class UserAlreadyExists(data: String): GlobalError(ErrorCode.USER_ALREADY_EXISTS, data) {
}