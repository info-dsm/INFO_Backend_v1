package com.info.info_v1_backend.domain.auth.business.dto.response

import com.info.info_v1_backend.domain.project.data.entity.type.ProjectStatus

data class ProjectList(
        val name: String,
        val codeLinkList: List<String>?,
        val projectStatus: ProjectStatus,
)
