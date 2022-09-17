package com.info.info_v1_backend.domain.company.data.repository.notice

import com.info.info_v1_backend.domain.company.data.entity.notice.embeddable.TargetMajor
import org.springframework.data.jpa.repository.JpaRepository

interface TargetMajorRepository: JpaRepository<TargetMajor, Long> {
}