package com.info.info_v1_backend.domain.project.business.controller.dto

import java.time.LocalDateTime

data class ProjectResponse(
    val name: String,
    val createAt: LocalDateTime?,
    val updateAt: LocalDateTime?,
    val createdBy: Long?,
    val updatedBy: Long?,
    val shortContent: String,
    val haveSeenCount: Long
)
