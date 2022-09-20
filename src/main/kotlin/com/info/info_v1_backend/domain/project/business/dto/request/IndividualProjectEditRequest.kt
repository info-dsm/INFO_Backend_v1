package com.info.info_v1_backend.domain.project.business.dto.request

import com.info.info_v1_backend.domain.project.business.dto.common.StudentIdDto

data class IndividualProjectEditRequest(
    val projectId: Long,
    val name: String,
    val shortContent: String,
    val githubLinkList: MutableList<String>,
    val studentIdList: MutableList<StudentIdDto>,
    val tagList: MutableList<String>
)
