package com.info.info_v1_backend.domain.project.data.repository

import com.info.info_v1_backend.domain.project.data.entity.project.RegisteredProject
import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface RegisteredProjectRepository: JpaRepository<RegisteredProject, Long> {

    fun findAllByStatus(status: ProjectStatus, pageable: Pageable): Page<RegisteredProject>
}