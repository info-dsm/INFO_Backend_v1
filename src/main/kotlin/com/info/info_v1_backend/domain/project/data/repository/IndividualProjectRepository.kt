package com.info.info_v1_backend.domain.project.data.repository

import com.info.info_v1_backend.domain.project.data.entity.project.IndividualProject
import org.springframework.data.jpa.repository.JpaRepository

interface IndividualProjectRepository: JpaRepository<IndividualProject, Long> {

}