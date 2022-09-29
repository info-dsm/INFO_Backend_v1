package com.info.info_v1_backend.domain.company.business.dto.response.notice

data class RecruitmentBusinessResponse(
    val bigClassificationResponseList: BigClassificationResponse,
    val smallClassificationResponse: SmallClassificationResponse,
    val numberOfEmployee: Int,
    val detailBusinessDescription: String?,
    val certificateList: List<String>,
    val gradeCutLine: Int?
)
