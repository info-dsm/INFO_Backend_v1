package com.info.info_v1_backend.domain.company.data.repository.notice

import com.info.info_v1_backend.domain.company.data.entity.notice.classification.RecruitmentBigClassification
import com.info.info_v1_backend.domain.company.data.entity.notice.classification.RecruitmentSmallClassification
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface RecruitmentSmallClassificationRepository: JpaRepository<RecruitmentSmallClassification, String> {

    fun findByNameAndAndBigClassification(name: String, bigClassification: RecruitmentBigClassification): Optional<RecruitmentSmallClassification>
}