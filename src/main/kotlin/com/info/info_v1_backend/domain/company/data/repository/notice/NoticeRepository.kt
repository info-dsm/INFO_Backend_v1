package com.info.info_v1_backend.domain.company.data.repository.notice

import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface NoticeRepository: JpaRepository<Notice, Long> {

    fun findAllByExpiredIsNot(expired: Boolean, pageable: Pageable): Page<Notice>
}