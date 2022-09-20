package com.info.info_v1_backend.domain.project.business.dto.request

data class ProjectStatusEditRequest(
    val projectId: Long,
    val status: Boolean
)
