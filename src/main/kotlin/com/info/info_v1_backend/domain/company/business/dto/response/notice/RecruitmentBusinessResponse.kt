package com.info.info_v1_backend.domain.company.business.dto.response.notice


data class RecruitmentBusinessResponse(
    val classificationResponse: ClassificationResponse,
    val detailBusinessDescription: String?,
    val languageSet: Set<LanguageResponse>,
    val technologySet: Set<TechnologyResponse>,
    val certificateList: List<CertificateResponse>,
    val numberOfEmployee: Int,
    val gradeCutLine: Int?
)
