package com.info.info_v1_backend.domain.company.business.dto.request.company

import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile

data class CompanyIntroductionRequest(
    val businessRegisteredCertificate: MultipartFile,
    val companyIntroductionFile: List<MultipartFile>,
    val companyLogo: MultipartFile?,
    val companyPhotoList: List<MultipartFile>
)
