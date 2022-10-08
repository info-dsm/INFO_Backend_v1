package com.info.info_v1_backend.domain.company.data.repository.company

import com.info.info_v1_backend.domain.company.data.entity.company.CompanySearchDocument
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.query.TextCriteria
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CompanySearchDocumentRepository: MongoRepository<CompanySearchDocument, ObjectId> {

    fun findByCompanyId(companyId: Long): Optional<CompanySearchDocument>
    fun findAllBy(textCriteria: TextCriteria, pageable: Pageable): Page<CompanySearchDocument>

}