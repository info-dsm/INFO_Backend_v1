package com.info.info_v1_backend.domain.board.business.dto

import com.info.info_v1_backend.domain.board.business.type.ClassInfo
import com.info.info_v1_backend.domain.company.data.entity.type.MajorType

data class PerClassEmployInfoDto(
    val classInfo: ClassInfo,
    val majorType: MajorType,
    val employRate: Int,
    val indicationDtoList: MutableList<IndicationDto>

)
