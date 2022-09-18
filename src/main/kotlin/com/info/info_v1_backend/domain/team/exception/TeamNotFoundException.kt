package com.info.info_v1_backend.domain.team.exception

import com.info.info_v1_backend.global.error.ErrorCode
import com.info.info_v1_backend.global.error.data.GlobalError

class TeamNotFoundException(data: String): GlobalError(ErrorCode.TEAM_NOT_FOUND, data) {
}