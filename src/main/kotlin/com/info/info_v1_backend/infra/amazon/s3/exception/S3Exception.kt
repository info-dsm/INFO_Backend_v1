package com.info.info_v1_backend.infra.amazon.s3.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class S3Exception(data: String): GlobalError(ErrorCode.S3_ERROR, data) {
}