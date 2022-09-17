package com.info.info_v1_backend.domain.company.business.dto.response.company

import com.info.info_v1_backend.domain.company.business.dto.response.comment.CommentResponse
import com.info.info_v1_backend.infra.amazon.s3.dto.ImageDto

data class MaximumCompanyResponse(
    val shortName: String,
    val fullName: String,
    val photoList: List<ImageDto>,
    val introduction: String,
    val employedCount: Int,
    val commentList: List<CommentResponse>

)
