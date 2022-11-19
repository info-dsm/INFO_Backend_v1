package com.info.info_v1_backend.domain.auth.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class IncorrectEmail(data: String) : GlobalError(ErrorCode.INCORRECT_EMAIL, data) {
}