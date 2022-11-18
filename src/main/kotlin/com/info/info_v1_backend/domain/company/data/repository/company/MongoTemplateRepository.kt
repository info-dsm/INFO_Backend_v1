package com.info.info_v1_backend.domain.company.data.repository.company

import com.info.info_v1_backend.domain.company.data.entity.company.CompanySearchDocument
import org.springframework.stereotype.Repository

@Repository
interface MongoTemplateRepository {
    fun findAllCompanyName(companyName: String): List<CompanySearchDocument>
}