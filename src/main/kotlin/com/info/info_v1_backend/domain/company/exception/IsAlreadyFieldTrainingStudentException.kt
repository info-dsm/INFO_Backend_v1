package com.info.info_v1_backend.domain.company.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class IsAlreadyFieldTrainingStudentException(data: String): GlobalError(ErrorCode.IS_ALREADY_FIELD_TRAINING_STUDENT, data) {
}