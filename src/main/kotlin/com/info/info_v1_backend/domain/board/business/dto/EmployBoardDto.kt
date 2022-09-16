package com.info.info_v1_backend.domain.board.business.dto

import com.info.info_v1_backend.domain.company.data.entity.type.MajorType

data class EmployBoardDto (
    val totalRate: Int,
    val bestMajor: MajorType,
    val perClassEmployInfoDtoList: MutableList<PerClassEmployInfoDto>

)