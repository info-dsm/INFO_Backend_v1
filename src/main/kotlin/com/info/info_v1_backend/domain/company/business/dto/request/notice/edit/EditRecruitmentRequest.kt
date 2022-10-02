package com.info.info_v1_backend.domain.company.business.dto.request.notice.edit

data class EditRecruitmentRequest(
    val numberOfEmployee: Int?,
    val detailBusinessDescription: String?,
    val gradeCutLine: Int?
)
