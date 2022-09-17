package com.info.info_v1_backend.domain.board.business.type

import com.info.info_v1_backend.domain.company.data.entity.type.MajorType

enum class ClassInfo(
    val classNum: Int,
    val major: MajorType
) {
    FIRST(1, MajorType.SW_DEV),
    SECOND(2, MajorType.SW_DEV),
    THIRD(3, MajorType.EMBEDEED),
    FOURTH(4, MajorType.SECURITY)
}