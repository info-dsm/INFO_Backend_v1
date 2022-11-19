package com.info.info_v1_backend.domain.company.business.dto.response.company

import com.info.info_v1_backend.domain.company.business.dto.response.comment.CommentResponse
import com.info.info_v1_backend.domain.company.data.entity.company.address.AddressInfo
import java.time.Year

data class MinimumCompanyResponse (
    val companyId: Long,
    val companyNumber: String,
    val contactorEmail: String,
    val companyName: String,
    val homeAddressInfo: AddressInfo,
    val businessTagged: List<BusinessAreaResponse>,
    val workerCount: Int,
    val annualSales: Long,
    val isLeading: Boolean,
    val isAssociated: Boolean,
    val latestNoticeYear: Year?,
    val totalHiredStudentCount: Int,
    val commentList: List<CommentResponse>,
    val companyIntroductionResponse: CompanyIntroductionResponse

)