package com.info.info_v1_backend.domain.company.data.repository.notice

import com.info.info_v1_backend.domain.company.data.entity.notice.classification.RecruitmentSmallClassification
import org.springframework.data.jpa.repository.JpaRepository

interface RecruitmentSmallClassificationRepository: JpaRepository<RecruitmentSmallClassification, String> {
}