package com.info.info_v1_backend.domain.company.business.dto.response.company

import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyContactRequest
import com.info.info_v1_backend.domain.company.business.dto.request.company.CompanyInformationRequest
import com.info.info_v1_backend.domain.company.business.dto.response.comment.CommentResponse
import java.time.LocalDate

data class MaximumCompanyResponse(
    val companyId: Long,
    val companyNumber: String,
    val companyName: String,
    val companyInformation: CompanyInformationRequest,
    val companyContact: CompanyContactRequest,
    val businessAreaResponseList: List<BusinessAreaResponse>,
    val companyIntroduction: CompanyIntroductionResponse,
    val commentList: List<CommentResponse>,
    val isLeading: Boolean,
    val isAssociated: Boolean,
    val lastNoticeDate: LocalDate?,
    val totalHiredStudentList: List<HiredStudentResponse>,

)
