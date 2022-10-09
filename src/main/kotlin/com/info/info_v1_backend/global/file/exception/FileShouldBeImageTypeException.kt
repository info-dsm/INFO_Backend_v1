package com.info.info_v1_backend.global.file.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError
import com.info.info_v1_backend.global.file.entity.type.ImageExt

class FileShouldBeImageTypeException(): GlobalError(ErrorCode.FILE_SHOULD_BE_IMAGE_TYPE, ImageExt.values().map {it.extension}.toString()) {
}