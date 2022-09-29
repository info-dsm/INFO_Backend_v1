package com.info.info_v1_backend.domain.company.business.dto.response.company

import com.info.info_v1_backend.global.file.dto.FileResponse

data class CompanyIntroductionResponse(
    val introduction: String,
    val businessRegisteredCertificate: FileResponse?,
    val companyIntroductionFile: List<FileResponse>,
    val companyLogo: FileResponse?,
    val companyPhotoList: List<FileResponse>

)
