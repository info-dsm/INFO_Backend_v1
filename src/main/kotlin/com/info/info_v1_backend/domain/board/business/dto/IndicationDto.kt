package com.info.info_v1_backend.domain.board.business.dto

import com.info.info_v1_backend.infra.amazon.s3.dto.ImageDto

data class IndicationDto (
    val companyId: Long,
    val companyShortName: String,
    val image: ImageDto
)