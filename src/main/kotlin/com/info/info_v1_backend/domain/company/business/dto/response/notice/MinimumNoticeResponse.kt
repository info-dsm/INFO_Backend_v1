package com.info.info_v1_backend.domain.company.business.dto.response.notice

import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse

data class MinimumNoticeResponse(
    val noticeId: Long,
    val company: MinimumCompanyResponse,
    val recruitmentBusinessResponse: RecruitmentBusinessResponse,
    val applicantCount: Int

)
