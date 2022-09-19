package com.info.info_v1_backend.domain.project.business.controller.dto.request

data class ProjectStatusEditRequest(
    val projectId: Long,
    val status: Boolean
)
