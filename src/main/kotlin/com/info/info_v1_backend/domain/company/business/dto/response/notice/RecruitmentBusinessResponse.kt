package com.info.info_v1_backend.domain.company.business.dto.response.notice


data class RecruitmentBusinessResponse(
    val bigClassificationResponse: BigClassificationResponse,
    val smallClassificationResponse: SmallClassificationResponse,
    val detailBusinessDescription: String?,
    val languageSet: Set<LanguageResponse>,
    val technologySet: Set<TechnologyResponse>,
    val certificateList: Set<CertificateResponse>,
    val numberOfEmployee: Int,
    val gradeCutLine: Int?
)
