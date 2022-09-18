package com.info.info_v1_backend.domain.company.data.repository.company

import com.info.info_v1_backend.domain.company.data.entity.company.CompanyCheckCode
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyCheckCodeRepository: JpaRepository<CompanyCheckCode, String> {
}