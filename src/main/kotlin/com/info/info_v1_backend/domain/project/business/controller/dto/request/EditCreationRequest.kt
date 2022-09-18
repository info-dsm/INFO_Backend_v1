package com.info.info_v1_backend.domain.project.business.controller.dto.request

data class EditCreationRequest(
    var id: Long?,
    var projectId: Long?,
    var studentId: String?
)
