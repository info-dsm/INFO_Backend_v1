package com.info.info_v1_backend.domain.company.data.repository.company

import com.info.info_v1_backend.domain.auth.data.entity.user.Student
import com.info.info_v1_backend.domain.company.data.entity.company.Company
import com.info.info_v1_backend.domain.company.data.entity.company.work.field.FieldTraining
import com.info.info_v1_backend.domain.company.data.entity.company.work.field.FieldTrainingIdClass
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Year
import java.util.Optional

interface FieldTrainingRepository: JpaRepository<FieldTraining, FieldTrainingIdClass> {

    fun findByStudentAndCompany(student: Student, company: Company): Optional<FieldTraining>
    fun findAllByCreatedAtBetween(startDate: LocalDateTime, endDate: LocalDateTime, pageable: Pageable): Page<FieldTraining>
}