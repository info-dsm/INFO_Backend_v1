package com.info.info_v1_backend.domain.company.business.dto.request.company

import org.springframework.web.multipart.MultipartFile
import javax.validation.constraints.Size

data class CompanyIntroductionRequest(
    @field:Size(
        max = 255,
        min = 10,
        message = "회사 소개는 10자 이상이여야합니다."
    )
    val introduction: String,
    val businessRegisteredCertificate: MultipartFile,
    val companyIntroductionFile: List<MultipartFile>,
    val companyLogo: MultipartFile?,
    val companyPhotoList: List<MultipartFile>
) {


}

