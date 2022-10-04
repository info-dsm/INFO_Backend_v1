package com.info.info_v1_backend.domain.company.data.repository.notice

import com.info.info_v1_backend.domain.company.data.entity.notice.technology.TechnologyUsage
import com.info.info_v1_backend.domain.company.data.entity.notice.technology.TechnologyUsageIdClass
import org.springframework.data.jpa.repository.JpaRepository

interface TechnologyUsageRepository: JpaRepository<TechnologyUsage, TechnologyUsageIdClass> {
}