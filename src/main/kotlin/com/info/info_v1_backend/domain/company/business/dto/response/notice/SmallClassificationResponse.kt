package com.info.info_v1_backend.domain.company.business.dto.response.notice

data class SmallClassificationResponse(
    val bigClassification: BigClassificationResponse,
    val name: String
)
