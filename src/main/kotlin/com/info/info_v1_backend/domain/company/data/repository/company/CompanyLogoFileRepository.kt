package com.info.info_v1_backend.domain.company.data.repository.company

import com.info.info_v1_backend.domain.company.data.entity.company.file.CompanyLogoFile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository

interface CompanyLogoFileRepository: JpaRepository<CompanyLogoFile, Long> {
}