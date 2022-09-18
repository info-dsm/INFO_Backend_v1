package com.info.info_v1_backend.domain.company.data.repository.company

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface CompanyRepository: JpaRepository<Company, String> {

    fun findAllByStudentListContains(student: Student): List<Company>
    fun findByIdAndStudentListContains(id: String, student: Student): Optional<Company>
}