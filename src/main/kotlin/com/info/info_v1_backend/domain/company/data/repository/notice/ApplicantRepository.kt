package com.info.info_v1_backend.domain.company.data.repository.notice

import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.domain.company.data.entity.notice.applicant.Applicant
import com.info.info_v1_backend.domain.company.data.entity.notice.applicant.ApplicantIdClass
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface ApplicantRepository: JpaRepository<Applicant, ApplicantIdClass> {

    fun findByNotice(notice: Notice, pageable: Pageable): Page<Applicant>
}