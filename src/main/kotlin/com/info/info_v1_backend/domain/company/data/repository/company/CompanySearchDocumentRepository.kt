package com.info.info_v1_backend.domain.company.data.repository.company

import com.info.info_v1_backend.domain.company.data.entity.company.CompanySearchDocument
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.query.TextCriteria
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional

interface CompanySearchDocumentRepository: MongoRepository<CompanySearchDocument, ObjectId> {

    fun findByCompanyId(companyId: String): Optional<CompanySearchDocument>
    fun findAllBy(criteria: TextCriteria, pageable: Pageable): Page<CompanySearchDocument>
    fun findByCompanyNameOrderByTextScoreDesc(companyName: String, pageable: Pageable): Page<CompanySearchDocument>

}