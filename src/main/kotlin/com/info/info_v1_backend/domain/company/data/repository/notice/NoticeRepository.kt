package com.info.info_v1_backend.domain.company.data.repository.notice

import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.domain.company.data.entity.notice.NoticeWaitingStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface NoticeRepository: JpaRepository<Notice, Long> {

    fun findAllByIsApprove(approve: NoticeWaitingStatus, pageable: Pageable): Page<Notice>
    fun findByIdAndCompanyAndIsApproveNot(id: Long, company: Company, notApproveStatus: NoticeWaitingStatus): Optional<Notice>
    fun findAllByCompany(company: Company): List<Notice>

}