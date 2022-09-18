package com.info.info_v1_backend.domain.company.business.dto.response.notice

import com.info.info_v1_backend.domain.company.business.dto.request.notice.TargetMajorRequest
import com.info.info_v1_backend.domain.company.business.dto.response.company.MinimumCompanyResponse

data class MinimumNoticeResponse(
    val noticeId: Long,
    val company: MinimumCompanyResponse,
    val targetMajorList: List<TargetMajorRequest>,
    val businessInformation: String,
    val certificateList: List<String>?,
    val cutLine: Int?,
    val personalRemark: String

)
