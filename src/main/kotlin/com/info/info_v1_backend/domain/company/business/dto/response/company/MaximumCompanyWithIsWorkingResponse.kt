package com.info.info_v1_backend.domain.company.business.dto.response.company

import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyContactRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyInformationRequest
import com.info.info_v1_backend.domain.company.business.dto.response.comment.CommentResponse
import java.time.LocalDate

data class MaximumCompanyWithIsWorkingResponse(
    val maximumCompanyResponse: MaximumCompanyResponse,
    val isWorking: Boolean
)
