package com.info.info_v1_backend.domain.company.data.repository.company

import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyPhotoFile
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyPhotoFileRepository: JpaRepository<CompanyPhotoFile, Long> {
}