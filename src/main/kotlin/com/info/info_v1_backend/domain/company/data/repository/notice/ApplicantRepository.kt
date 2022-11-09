package com.info.info_v1_backend.domain.company.data.repository.notice

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.company.data.entity.notice.Notice
import com.info.info_v1_backend.domain.company.data.entity.notice.applicant.Applicant
import com.info.info_v1_backend.domain.company.data.entity.notice.applicant.ApplicantIdClass
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ApplicantRepository: JpaRepository<Applicant, ApplicantIdClass> {

    fun findByNotice(notice: Notice): List<Applicant>
    fun findByNoticeAndStudent(notice: Notice, student: Student): Optional<Applicant>

    fun existsByNoticeAndStudent(notice: Notice, student: Student): Boolean
}