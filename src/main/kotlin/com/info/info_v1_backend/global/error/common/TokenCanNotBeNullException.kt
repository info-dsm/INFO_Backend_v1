package com.info.info_v1_backend.global.error.common

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class TokenCanNotBeNullException(): GlobalError(ErrorCode.TOKEN_IS_NULL, "Token is Null") {
}