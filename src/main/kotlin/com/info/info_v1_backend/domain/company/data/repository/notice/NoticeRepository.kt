package com.info.info_v1_backend.domain.company.data.repository.notice

import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface NoticeRepository: JpaRepository<Notice, Long> {

    fun findAllByApprove(approve: Boolean, pageable: Pageable): Page<Notice>
    fun findByIdAndCompany(id: Long, company: Company): Optional<Notice>

}