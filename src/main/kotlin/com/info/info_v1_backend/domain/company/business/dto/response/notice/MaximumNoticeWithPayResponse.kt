package com.info.info_v1_backend.domain.company.business.dto.response.notice

import com.info.info_v1_backend.domain.company.business.dto.request.notice.register.*
import com.info.info_v1_backend.domain.company.business.dto.response.company.MaximumCompanyResponse
import com.info.info_v1_backend.domain.company.data.entity.notice.interview.InterviewProcess
import com.info.info_v1_backend.global.file.dto.FileResponse

data class MaximumNoticeWithPayResponse(
    val notice: MaximumNoticeWithoutPayResponse,
    val pay: PayRequest

)
