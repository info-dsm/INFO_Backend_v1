package com.info.info_v1_backend.domain.company.data.repository.notice

import com.info.info_v1_backend.domain.company.data.entity.notice.applicant.Applicant
import com.info.info_v1_backend.domain.company.data.entity.notice.applicant.ApplicantIdClass
import org.springframework.data.jpa.repository.JpaRepository

interface ApplicantRepository: JpaRepository<Applicant, ApplicantIdClass> {

}