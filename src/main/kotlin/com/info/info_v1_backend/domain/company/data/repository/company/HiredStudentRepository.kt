package com.info.info_v1_backend.domain.company.data.repository.company

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.company.work.hired.HiredStudent
import com.info.info_v1_backend.domain.company.data.entity.company.work.hired.HiredStudentIdClass
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface HiredStudentRepository: JpaRepository<HiredStudent, HiredStudentIdClass> {

    fun findByStudentAndCompany(student: Student, company: Company): Optional<HiredStudent>
    fun findAllByStudent(student: Student): List<HiredStudent>
}