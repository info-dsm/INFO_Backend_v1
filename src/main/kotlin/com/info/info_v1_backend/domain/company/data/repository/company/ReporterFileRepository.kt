package com.info.info_v1_backend.domain.company.data.repository.company

import com.info.info_v1_backend.domain.company.data.entity.notice.file.Reporter
import org.springframework.data.jpa.repository.JpaRepository

interface ReporterFileRepository: JpaRepository<Reporter, Long> {
}