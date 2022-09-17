package com.info.info_v1_backend.domain.company.business.dto.response.company

import com.info.info_v1_backend.infra.amazon.s3.dto.ImageDto

data class MinimumCompanyResponse (
    val shortName: String,
    val fullName: String,
    val photoList: List<ImageDto>,
    val introduction: String

)