package com.info.info_v1_backend.domain.board.business.dto

import com.info.info_v1_backend.global.file.dto.FileResponse

data class IndicationDto (
    val companyId: Long,
    val companyName: String,
    val image: FileResponse
)