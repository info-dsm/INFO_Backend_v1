package com.info.info_v1_backend.domain.company.data.repository.notice

import com.info.info_v1_backend.domain.company.data.entity.notice.NoticeSearchDocument
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.query.TextCriteria
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.Optional

interface NoticeSearchDocumentRepository: MongoRepository<NoticeSearchDocument, ObjectId> {

    fun findAllByCompanyId(companyId: Long): List<NoticeSearchDocument>
    fun findByNoticeId(noticeId: Long): Optional<NoticeSearchDocument>
    fun findAllBy(criteria: TextCriteria, pageable: Pageable): Page<NoticeSearchDocument>
}