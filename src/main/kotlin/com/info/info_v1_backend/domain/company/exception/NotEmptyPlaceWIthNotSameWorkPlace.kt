package com.info.info_v1_backend.domain.company.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class NotEmptyPlaceWIthNotSameWorkPlace(): GlobalError(ErrorCode.NOT_EMPTY_PLACE_WITH_NOT_SAME_WORK_PLACE, "work place has no data") {
}