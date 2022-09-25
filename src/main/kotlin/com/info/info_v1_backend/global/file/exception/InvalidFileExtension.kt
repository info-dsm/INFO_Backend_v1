package com.info.info_v1_backend.global.file.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class InvalidFileExtension(data: String): GlobalError(ErrorCode.INVALID_FILE_EXTENSION, data) {
}