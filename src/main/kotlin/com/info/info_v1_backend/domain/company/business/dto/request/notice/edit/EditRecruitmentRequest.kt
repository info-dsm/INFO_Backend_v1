package com.info.info_v1_backend.domain.company.business.dto.request.notice.edit

data class EditRecruitmentRequest(
    val recruitmentBusinessId: Long,
    val detailBusinessDescription: String?,
    val numberOfEmployee: Int?,
    val gradeCutLine: Int?
)
