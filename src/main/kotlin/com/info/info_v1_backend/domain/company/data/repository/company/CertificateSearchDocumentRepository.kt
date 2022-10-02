package com.info.info_v1_backend.domain.company.data.repository.company

import com.info.info_v1_backend.domain.company.data.entity.notice.certificate.CertificateSearchDocument
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.repository.MongoRepository

interface CertificateSearchDocumentRepository: MongoRepository<CertificateSearchDocument, ObjectId> {

    fun findByCertificateNameOrderByTextScoreDesc(certificateName: String, pageable: Pageable): Page<CertificateSearchDocument>
}