package com.info.info_v1_backend.domain.company.data.repository.notice

import com.info.info_v1_backend.domain.company.data.entity.notice.language.Language
import com.info.info_v1_backend.domain.company.data.entity.notice.language.LanguageUsage
import com.info.info_v1_backend.domain.company.data.entity.notice.language.LanguageUsageIdClass
import com.info.info_v1_backend.domain.company.data.entity.notice.recruitment.RecruitmentBusiness
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface LanguageUsageRepository: JpaRepository<LanguageUsage, LanguageUsageIdClass> {

    fun findByLanguageAndRecruitmentBusiness(language: Language, recruitmentBusiness: RecruitmentBusiness): Optional<LanguageUsage>
    fun deleteByLanguageAndRecruitmentBusiness(language: Language, recruitmentBusiness: RecruitmentBusiness)
}