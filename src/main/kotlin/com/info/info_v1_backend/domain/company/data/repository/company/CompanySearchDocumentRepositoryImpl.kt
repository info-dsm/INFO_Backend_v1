package com.info.info_v1_backend.domain.company.data.repository.company

import com.info.info_v1_backend.domain.company.data.entity.company.CompanySearchDocument
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

class CompanySearchDocumentRepositoryImpl(
    private val mongoTemplate: MongoTemplate,
):MongoTemplateRepository {
    override fun findAllCompanyName(companyName: String): List<CompanySearchDocument> {
        val query = Query()

        query.addCriteria(
            Criteria.where("companyName").regex(companyName)
        )
        return mongoTemplate.find(query, CompanySearchDocument::class.java)
    }

}