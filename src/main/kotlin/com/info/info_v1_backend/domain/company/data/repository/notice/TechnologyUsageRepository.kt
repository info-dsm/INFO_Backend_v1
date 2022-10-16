package com.info.info_v1_backend.domain.company.data.repository.notice

import com.info.info_v1_backend.domain.company.data.entity.notice.language.Language
import com.info.info_v1_backend.domain.company.data.entity.notice.recruitment.RecruitmentBusiness
import com.info.info_v1_backend.domain.company.data.entity.notice.technology.Technology
import com.info.info_v1_backend.domain.company.data.entity.notice.technology.TechnologyUsage
import com.info.info_v1_backend.domain.company.data.entity.notice.technology.TechnologyUsageIdClass
import org.springframework.data.jpa.repository.JpaRepository

interface TechnologyUsageRepository: JpaRepository<TechnologyUsage, TechnologyUsageIdClass> {

    fun deleteByTechnologyAndRecruitmentBusiness(technology: Technology, recruitmentBusiness: RecruitmentBusiness)
}