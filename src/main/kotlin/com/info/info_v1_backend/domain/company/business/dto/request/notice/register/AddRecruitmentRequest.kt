package com.info.info_v1_backend.domain.company.business.dto.request.notice.register

data class AddRecruitmentRequest (
    val bigClassification: String,
    val smallClassification: String,
    val numberOfEmployee: Int,
    val detailBusinessDescription: String?,
    val gradeCutLine: Int?,
    val needCertificateList: List<String>
)
