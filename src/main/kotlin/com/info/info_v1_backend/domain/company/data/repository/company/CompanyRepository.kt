package com.info.info_v1_backend.domain.company.data.repository.company

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.company.work.hired.HiredStudent
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CompanyRepository: JpaRepository<Company, Long> {
    fun findAllByHiredStudentListContains(hiredStudent: HiredStudent): List<Company>
    fun findAllByNoticeRegisteredYearListContains(year: Int, pageable: Pageable): Page<Company>
}