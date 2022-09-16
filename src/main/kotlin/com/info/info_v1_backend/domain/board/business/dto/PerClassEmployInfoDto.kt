package com.info.info_v1_backend.domain.board.business.dto

import com.info.info_v1_backend.domain.board.data.entity.type.ClassNum
import com.info.info_v1_backend.domain.company.data.entity.type.MajorType

data class PerClassEmployInfoDto(
    val classNum: ClassNum,
    val majorType: MajorType,
    val indicationDtoList: MutableList<IndicationDto>

)
