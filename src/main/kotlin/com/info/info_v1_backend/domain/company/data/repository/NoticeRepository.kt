package com.info.info_v1_backend.domain.company.data.repository

import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import org.springframework.data.jpa.repository.JpaRepository

interface NoticeRepository: JpaRepository<Notice, Long> {
}