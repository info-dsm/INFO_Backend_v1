package com.info.info_v1_backend.domain.company.business.dto.response.company

import com.info.info_v1_backend.domain.company.business.dto.response.comment.CommentResponse
import com.info.info_v1_backend.infra.amazon.s3.dto.ImageDto
import java.time.Year

data class MaximumCompanyResponse(
    val companyNumber: String,
    val shortName: String,
    val fullName: String,
    val photoList: List<ImageDto>,
    val establishedAt: Year,
    val annualSales: Long,
    val introduction: String,
    val employedCount: Int,
    val industry: String?,
    val mainProduct: String?,
    val address: String,
    val companyPlace: String,
    val commentList: List<CommentResponse>,

)
