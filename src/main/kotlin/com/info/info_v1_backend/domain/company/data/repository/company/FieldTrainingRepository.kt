package com.info.info_v1_backend.domain.company.data.repository.company

import com.info.info_v1_backend.domain.company.data.entity.company.work.field.FieldTraining
import com.info.info_v1_backend.domain.company.data.entity.company.work.field.FieldTrainingIdClass
import org.springframework.data.jpa.repository.JpaRepository

interface FieldTrainingRepository: JpaRepository<FieldTraining, FieldTrainingIdClass> {
}